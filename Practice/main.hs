import Data.List

-- First comment

{-
Multi
line
comments
-}

-- permanent value for variable
permanent::Int
permanent = 3

-- expressions
sumOfNums = sum[1..100]
addExpr = 5+3
subExpr = 5-3
multExpr = 5*3
divExpr = 5/3

nextExpr = multExpr/subExpr

-- prefix operator
modExpr = mod 5 4
-- infix operator
modExpr2 = 5 `mod` 4

-- negative number expression
negNumExpr = 15 + (-4)

-- built in math functions
piVal = pi
ePow9 = exp 9
logOf9 = log 9
squared9 = 9 ** 2
truncateVal = truncate 9.999
roundVal = round 9.999
ceilingVal = ceiling 9.999
floorVal = floor 9.999

-- concatenation of two lists
primeNumber = [3,5,7,11]
morePrime = primeNumber ++ [13,17,19,23,29]

-- 'cons' operator (Concatenation)
favNums = 2:7:21:66:[]
morePrime2 = 2:morePrime

-- length of list
lenPrime = length morePrime2
-- reverse of list
revPrime = reverse morePrime2
-- check whether list is empty
isListEmpty = null morePrime2
-- first, second and last element of list
firstPrime = head morePrime2
secondPrime = morePrime2 !! 1
lastPrime = last morePrime2

-- first n elements of list
first3Primes = take 3 morePrime2
-- remove n elements from list
removedPrimes = drop 3 morePrime2
-- check whether an element is in list
is7inList = 7 `elem` morePrime2
-- maximum and minimum element in list
maxPrime = maximum morePrime2
minPrime = minimum morePrime2

-- product of elements in list
smallList = [3,4,5]
prodList = product smallList

-- generating sequence of elements in list
zeroToTen = [0..10]
evenList = [2,4..10]
letterList = ['A','C'..'Z']

-- infinite list
infinPow10 = [10,20..]

-- repetition
many2s = take 10 (repeat 2)
many3s = replicate 10 3

-- cyclic list
cycleList = take 10 (cycle[1,2,3,4,5])

-- generating a list multiplying elements by an integer
listTime2 = [x*2 | x <- [1..10]]
listTime3 = [x*3 | x <- [1..20], x*3 <= 50]

-- finding elements divisible by 9 and 13
divBy9AND13 = [x | x <- [1..500], x `mod` 13 == 0, x `mod` 9 == 0]

-- sort a list
sList = sort [9,1,7,5,4,3,2,10,8,6]
unsorted = [20,6,3,89]
sorted = sort unsorted

-- sum of lists
sumOfList = zipWith (+) [1,2,3,4,5] [6,7,8,9,10]
sumOfList2 = zipWith (+) [1,2,3,4,5] [6,7,8,9]
sumOfList3 = zipWith (+) [1,2,3,4] [6,7,8,9,10]

-- check the elements bigger/less than n
listBiggerThan5 = filter (>5) morePrime2
listLessThan13 = filter (<13) [2,3,5,7,23,7,19,32]

-- even numbers upto 20
evenUpto20 = takeWhile (<= 20) [2,4..]

-- fold, foldl: left to right operation
-- foldr: right to left operation
multOfList = foldl (*) 1 [2,3,4,5]
subOfList = foldl (-) 0 [1,2,3]     -- (((0-1)-2)-3)
subOfList2 = foldr (-) 0 [1,2,3]    -- 1-(2-(3-0))

-- list comprehenstion
pow3List = [3^n | n <- [1..10]]

-- multiplication table
multTable = [[x*y | y <- [1..10]] | x <- [1..10]]

-- random tuple
randTuple = (1, "Random Tuple")
bobSmith = ("Bob Smith", 52)
-- first element of tuple
bobsName = fst bobSmith
-- second element of tuple
bobsAge = snd bobSmith

-- funcName param1 param2 = operation(return value)
addMe :: Int -> Int -> Int
addMe x y = x+y

-- user type declaration
addTuples :: (Int, Int) -> (Int, Int) -> (Int, Int)
addTuples (x, y) (x2, y2) = (x+x2, y+y2)

whatAge :: Int -> String
whatAge 16 = "You can drive"
whatAge 18 = "You can vote"
whatAge 21 = "You are adult"
whatAge x = "Nothing important"

-- factorial by recursion
factorial :: Int -> Int
factorial 0 = 1
factorial n = n * factorial(n-1)

-- factorial by product
prodFactorial n = product [1..n]

-- where clause
batAvgRating :: Double -> Double -> String
batAvgRating hits atBats
    | avg <= 0.2 = "Terrible batting"
    | avg <= 0.25 = "Average player"
    | avg <= 0.28 = "Pretty good"
    | otherwise = "Superstar"
    where avg = hits/atBats

-- function mapping to list
times4 :: Int -> Int
times4 x = x*4
listTimes4 = map times4[1,2,3,4,5]

-- List splitting
multBy4 :: [Int] -> [Int]
multBy4 [] = []
multBy4 (x:xs) = times4 x : multBy4 xs

-- Compare two strings
areStringEq :: [Char] -> [Char] -> Bool
areStringEq [] [] = True
areStringEq (x:xs) (y:ys) = (x == y) && (areStringEq xs ys)
areStringEq _ _ = False

-- receive a function
doMult :: (Int -> Int) -> Int
doMult func = func 3
num3Times4 = doMult times4

-- return a function
times5 :: Int -> Int
times5 x = x*5
getAddFunc :: Int -> (Int -> Int)
getAddFunc x y = x+y
adds3 = getAddFunc 3
fourPlus3 = adds3 4

-- Comparison
-- <       --less than
-- >       --greater than
-- <=      --less than equal to
-- >=      --greater than equal to
-- ==      --equal to

-- Logical
-- &&       --AND
-- ||       --OR
-- not      --NOT

doubleEvenNumber y =
    if (y `mod` 2 /= 0)
        then y
        else y*2

-- case
getClass n = case n of
    5 -> "Go to kindergarten"
    6 -> "Go to elementary school"
    _ -> "Go away"

-- custom data type; able to show customer details
data Customer = Customer String String Double
    deriving Show

mohitKumar :: Customer
mohitKumar = Customer "Mohit Kumar" "IITG" 12000

getBalance :: Customer -> Double
getBalance (Customer _ _ b) = b

-- custom data type; able to check for equality and show details
data Employee = Employee {name :: String, position :: String, idNum :: Int}
    deriving (Eq, Show)

ankitKumar = Employee {name="Ankit Kumar", position="Manager", idNum=1}
darshitPatel = Employee {name="Darshit Patel", position="Salesman", idNum=2}
isAnkitDarshit = ankitKumar == darshitPatel
ankitKumarData = show ankitKumar
darshitPatelData = show darshitPatel

-- hello world
main = do
    putStrLn "Hello world!"