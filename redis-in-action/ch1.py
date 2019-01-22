import redis
import time

ARTICLES_PER_PAGE = 25
ONE_WEEK_IN_SECONDS = 7 * 24 * 3600
VOTE_SCORE = 432

# data use: key --> type
#  time: --> zset (key: article, value: time expires)
#  voted:article_id --> set  if a user voted before.
#  score: --> zset (key: article, value: article score)
#  votes: --> hash  (key:article, value: votes)
#  article: --> string(integer)
#  article:article_id --> hash (store article info)


def article_vote(conn, user, article):
    """
    vote an article
    :param conn: redis connection
    :param user: (user:user_id)
    :param article: (article:article_id)
    :return:
    """
    time_before_week = time.time() - ONE_WEEK_IN_SECONDS
    if conn.zscore("time:", article) < time_before_week:
        # the article is expired
        return
    article_id = article.split(":")[-1]
    if conn.sadd("voted:" + article_id, user):
        conn.zincrby("score:", article, VOTE_SCORE)
        conn.hincrby(article, 'votes', 1)


def post_article(conn, user, title, link):
    """
    post an article
    :param conn: redis connection
    :param user:
    :param title: article title
    :param link: article link
    :return:
    """
    article_id = conn.incr("article:")
    voted = "voted:" + article_id
    conn.sadd(voted, user)
    conn.expire(ONE_WEEK_IN_SECONDS)  # set votes expire time
    now = time.time()
    article = "article:" + article_id
    conn.hmset(article, {
        "title": title,
        "link": link,
        "poster": user,
        "time": now,
        "votes": 1
    })
    conn.zadd("score:", article, now + VOTE_SCORE)
    conn.zadd("time:", article, now)


def get_articles(conn, page, order="score:"):
    start = (page - 1) * ARTICLES_PER_PAGE
    end = start + ARTICLES_PER_PAGE - 1
    ids = conn.zrevrange(order, start, end)
    articles = []
    for a_id in ids:
        article_data = conn.hgetall(id)
        article_data["id"] = a_id
        articles.append(a_id)
    return articles
