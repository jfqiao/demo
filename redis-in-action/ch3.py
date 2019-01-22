
def test(a, b, c):
    if a < b < c:
        return True
    return False


def test_valid(nums, n):
    for i in range(n - 1):
        for j in range(i + 1, n):
            for k in range(i + 1, j):
                a = nums[i]
                b = nums[k]
                c = nums[j]
                if test(a, b, c) or test(-a, b, c) or test(a, -b, c) or test(a, b, -c) \
                    or test(-a, -b, c) or test(-a, b, -c) or test(a, -b, -c) or test(-a, -b, -c):
                    continue
                else:
                    return False
    return True


if __name__ == "__main__":
    n = int(input())
    strs = input().split(" ")
    nums = []
    for i in range(n):
        nums.append(int(strs[i]))
    if not test_valid(nums, n):
        print(-1)
    else:
        dec_num = 0
        for i in range(n - 1):
            if nums[i] > nums[i + 1]:
                dec_num += 1
        print(dec_num)
