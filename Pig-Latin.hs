pigLatin :: String -> String -> String
pigLatin str acc -- acc should initially be ""
    | (head str) `elem` vowels = str ++ acc ++ "ay"
    | otherwise = pigLatin (tail str) [head str]
    where vowels = "aeiouAEIOU"
