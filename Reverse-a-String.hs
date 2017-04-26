reverseString :: [Char] -> [Char]
reverseString [] = []
reverseString [x] = [x]
reverseString (x:xs) = (reverseString xs) ++ [x]
