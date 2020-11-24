-- Import required libraries
import Data.List

-- Checks whether a set is empty or not
isEmpty list = null list

-- Base case for removeDuplicates: If list is empty, return empty list
removeDuplicates [] = []
-- Removes duplicates from a list to convert it to a set
removeDuplicates (x:list) =
    -- If x is present in remaining list, do not add it to new list and call
    -- removeDuplicates recursively for remaining list
    if elem x list then
        removeDuplicates list
    -- Else, add x and call removeDuplicates recursively for remaining list
    else
        x : removeDuplicates list

-- Returns union of two sets by concatenating both lists and removing
-- duplicates from new list
unionSet list1 list2 = removeDuplicates (list1 ++ list2)

-- Base case for intersection: if list1 is empty, return empty set
intersection [] _ = []
-- Returns intersection of two sets
intersection (x:list1) list2 =
    -- If element x of list1 is also present in list2, then we add x to new list
    -- and call intersection recursively for remaining list1
    if elem x list2 then
        unionSet [x] (intersection list1 list2)
    -- Else, call intersection recursively for remaining list1
    else
        intersection list1 list2

-- Base case for subtraction: If set1 is empty, return empty set
subtraction [] _ = []
-- Subtracts set2 from set1
subtraction (x:list1) list2 =
    -- If element x of list1 is also present in list2, then do not add x in new
    -- list and call subtraction recursively for remaining list1
    if elem x list2 then
        subtraction list1 list2
    -- Else, add x to new list and call subtraction recursively for remaining list1
    else
        unionSet [x] (subtraction list1 list2)

-- Base case for addition: If any set is empty, return empty set
addition [] _ = []
addition _ [] = []
-- Performs addition on two sets and returns output set by adding x+y to new set
-- and calling addition recursively for list1 & remaining list2, and remaining list1 & list2
addition (x:list1) (y:list2) = unionSet [x+y] (unionSet (addition (x:list1) list2) (addition list1 (y:list2)))