
class Node(object):

    def __init__(self, name):
        self.name = name
        self.edge_node = {}


def dfs_collect(root_node, collected_keys, visit_map, node_map):
    if root_node is None:
        return
    if visit_map.get(root_node.name) is not None:
        return
    if root_node.name not in collected_keys:
        collected_keys.append(root_node.name)
    edges = root_node.edge_node.keys()
    visit_map[root_node.name] = root_node
    for edge in edges:
        if edge in collected_keys:
            conn_node_name = root_node.edge_node[edge]
            if conn_node_name not in collected_keys:
                collected_keys.append(conn_node_name)
            dfs_collect(node_map[conn_node_name], collected_keys, visit_map, node_map)


if __name__ == "__main__":
    n, m = input().split(" ")
    n = int(n)
    m = int(m)
    nodes = {}
    for i in range(m):
        strs = input().split(" ")
        node_left = nodes.get(int(strs[1]))
        if node_left is None:
            node_left = Node(int(strs[1]))
            nodes[node_left.name] = node_left
        node_right = nodes.get(int(strs[2]))
        if node_right is None:
            node_right = Node(int(strs[2]))
            nodes[node_right.name] = node_right
        node_left.edge_node[int(strs[0])] = node_right.name
        node_right.edge_node[int(strs[0])] = node_left.name
    node_one = nodes.get(1)
    if len(node_one.edge_node) == 0:
        print(1)
    collected_keys = []
    start_len = len(collected_keys)
    while True:
        visit_map = {}
        dfs_collect(node_one, collected_keys, visit_map, nodes)
        if start_len == len(collected_keys):
            break
        start_len = len(collected_keys)
