import Data.List

isEmpty list = null list

removeDuplicates [] = []
removeDuplicates (x:list) = if elem x list then
        removeDuplicates list
    else
        x : removeDuplicates list

unionSet list1 list2 = removeDuplicates (list1 ++ list2)

intersection [] _ = []
intersection (x:list1) list2 = if elem x list2 then
        unionSet [x] (intersection list1 list2)
    else
        intersection list1 list2

subtraction [] _ = []
subtraction (x:list1) list2 = if elem x list2 then
        subtraction list1 list2
    else
        unionSet [x] (subtraction list1 list2)

addition [] _ = []
addition _ [] = []
addition (x:list1) (y:list2) = unionSet [x+y] (unionSet (addition (x:list1) list2) (addition list1 (y:list2))) 