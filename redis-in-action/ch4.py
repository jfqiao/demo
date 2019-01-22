
if __name__ == "__main__":
    x = y = z = 7
    result = 0
    for i in range(1, x + 1):
        for j in range(1, y + 1):
            for k in range(1, z + 1):
                if i + j > k and i + k > j and j + k > i:
                    result += 1
    print(result)
