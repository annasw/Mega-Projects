isPalindrome :: String -> Bool
isPalindrome [] = True
isPalindrome [x] = True
isPalindrome xs
    | head xs /= last xs = False
    | otherwise = isPalindrome $ tail $ init xs
