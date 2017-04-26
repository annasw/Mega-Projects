countVowels :: String -> Int
countVowels [] = 0
countVowels [x] = if x `elem` vowels then 1 else 0
    where vowels = "aeiouAEIOU"
countVowels (x:xs)
    | x `elem` vowels = 1 + countVowels xs
    | otherwise = countVowels xs
    where vowels = "aeiouAEIOU"
