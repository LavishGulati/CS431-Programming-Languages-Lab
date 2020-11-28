-- Finds all possible dimensions given smallest dimension (a1, b1) and largest dimension (a2, b2)
-- NOTE: a1 == a2 == a
possibleAreasHelper from to =
    -- If b1 == b2, the only possibility is (a, b) s.t. b == b1 == b2
    if snd from == snd to then
        [from]
    -- If b1 != b2, we need to get (a, b1) and recursively call possibleAreasHelper for (a, b1+1)
    else
        from : possibleAreasHelper new_from to
    where new_from = (fst from, snd from + 1)

-- Finds all possible dimensions given smallest dimension (a1, b1) and largest dimension (a2, b2)
-- NOTE: a1 != a2
possibleAreas from to =
    -- If a1 == a2, we need to get (a1, b) s.t. b ranges from b1 to b2
    if fst from == fst to then
        possibleAreasHelper from to
    -- If a1 != a2, we need to get (a1, b) s.t. b ranges from b1 to b2
    -- and call possibleAreas recursively for (a1+1, b1), (a2, b2)
    else
        possibleAreasHelper from to ++ possibleAreas new_from to
    where
        new_from = (fst from + 1, snd from)

-- Forms tuple (a, b) given list as and bs
combinations1 as bs = [(a, b) | a <- as, b <- bs]

-- Forms tuple (a, b, c) given list as and cs
combinations2 as cs = [(a, b, c) | (a,b) <- as, c <- cs]

-- Forms tuple (a, b, c, d) given list as and ds
combinations3 as ds = [(a, b, c, d) | (a,b,c) <- as, d <- ds]

-- Base case for redundant3: Return empty list if input list is empty
redundant3 [] _ _ _ _ _ = []
-- Removes redundant combinations with same total area
redundant3 ((x,y,z,p):comb3) unique num_bedroom num_hall num_kitchen num_bathroom =
    -- If total area is already present in unique list, then call redundant3 recursively
    -- for rest of the list
    if elem (fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen
        +fst(p)*snd(p)*num_bathroom) unique then
        
        redundant3 comb3 unique num_bedroom num_hall num_kitchen num_bathroom
    -- Else, put the total area in unique list and call redundant3 recursively for rest
    -- of the list and append (x,y) to the answer
    else
        (x,y,z,p) : redundant3 comb3 (fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall
            +fst(z)*snd(z)*num_kitchen+fst(p)*snd(p)*num_bathroom:unique) num_bedroom
            num_hall num_kitchen num_bathroom

-- Forms tuple (a, b, c, d, e) given list as and es
combinations4 as es = [(a, b, c, d, e) | (a,b,c,d) <- as, e <- es]

-- Base case for redundant4: Return empty list if input list is empty
redundant4 [] _ _ _ _ _ _ = []
-- Removes redundant combinations with same total area
redundant4 ((x,y,z,p,q):comb4) unique num_bedroom num_hall num_kitchen num_bathroom num_garden =
    -- If total area is already present in unique list, then call redundant4 recursively
    -- for rest of the list
    if elem (fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen
        +fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden) unique then
        
        redundant4 comb4 unique num_bedroom num_hall num_kitchen num_bathroom num_garden
    -- Else, put the total area in unique list and call redundant4 recursively for rest
    -- of the list and append (x,y) to the answer
    else
        (x,y,z,p,q) : redundant4 comb4 (fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall
            +fst(z)*snd(z)*num_kitchen+fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden
            :unique) num_bedroom num_hall num_kitchen num_bathroom num_garden

-- Forms tuple (a, b, c, d, e, f) given list as and fs
combinations5 as fs = [(a, b, c, d, e, f) | (a,b,c,d,e) <- as, f <- fs]

-- Base case for redundant5: Return empty list if input list is empty
redundant5 [] _ _ _ _ _ _ _ = []
-- Removes redundant combinations with same total area
redundant5 ((x,y,z,p,q,r):comb5) unique num_bedroom num_hall num_kitchen num_bathroom num_garden num_balcony =
    -- If total area is already present in unique list, then call redundant5 recursively
    -- for rest of the list
    if elem (fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen
        +fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden+fst(r)*snd(r)*num_balcony)
        unique then
        
        redundant5 comb5 unique num_bedroom num_hall num_kitchen num_bathroom num_garden
            num_balcony
    -- Else, put the total area in unique list and call redundant5 recursively for rest
    -- of the list and append (x,y) to the answer
    else
        (x,y,z,p,q,r) : redundant5 comb5 (fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall
            +fst(z)*snd(z)*num_kitchen+fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden
            +fst(r)*snd(r)*num_balcony:unique) num_bedroom num_hall num_kitchen num_bathroom
            num_garden num_balcony

-- Base case for maximumArea: Return 0 if input list is empty
maximumArea [] _ _ _ _ _ _ = 0
-- Returns maximum total area from the list of combinations
maximumArea ((x,y,z,p,q,r):new_comb5) num_bedroom num_hall num_kitchen num_bathroom num_garden num_balcony =
    -- Call maximumArea recursively for rest of the list and choose the maximum value
    -- between current total area and recursive answer
    maximum [fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen
        +fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden+fst(r)*snd(r)*num_balcony,
        maximumArea new_comb5 num_bedroom num_hall num_kitchen num_bathroom num_garden
        num_balcony]

-- Prints dimensions of all components given area, number of bedrooms and number of halls
design :: Integer -> Integer -> Integer -> IO()
design area num_bedroom num_hall = do
    -- Get all possible dimensions of the components
    let poss_area_bedroom = possibleAreas (10,10) (15,15)
    let poss_area_hall = possibleAreas (15,10) (20,15)
    let poss_area_kitchen = possibleAreas (7,5) (15,13)
    let poss_area_bathroom = possibleAreas (4,5) (8,9)
    let poss_area_balcony = possibleAreas (5,5) (10,10)
    let poss_area_garden = possibleAreas (10,10) (20,20)

    -- one kitchen for up to three bedrooms
    let num_kitchen = ceiling(fromIntegral num_bedroom/3)::Integer
    -- number of bathrooms is one more than the number of bedrooms
    let num_bathroom = num_bedroom + 1
    -- one garden and one balcony
    let num_garden = 1
    let num_balcony = 1

    -- Combine bedroom and hall dimensions to form combination tuple: (bedroom, hall)
    let comb1 = combinations1 poss_area_bedroom poss_area_hall
    -- Keep only those tuples whose total area is less than or equal to given area
    let new_comb1 = filter (\(x,y) -> fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall <= area) comb1

    -- Combine previous and kitchen dimensions to form combination tuple: (bedroom, hall, kitchen)
    let comb2 = combinations2 new_comb1 poss_area_kitchen
    -- Keep only those tuples whose total area is less than or equal to given area
    let filter_comb2 = filter (\(x,y,z) -> fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen <= area) comb2
    -- dimension of a kitchen must not be larger than that of a hall or a bedroom
    let new_comb2 = filter (\(x,y,z) -> fst(z) <= fst(x) && fst(z) <= fst(y) && snd(z) <= snd(x) && snd(z) <= snd(y)) filter_comb2

    -- Combine previous and bathroom dimensions to form combination tuple: (bedroom, hall, kitchen, bathroom)
    let comb3 = combinations3 new_comb2 poss_area_bathroom
    -- Keep only those tuples whose total area is less than or equal to given area
    let filter_comb3 = filter (\(x,y,z,p) -> fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen+fst(p)*snd(p)*num_bathroom <= area) comb3
    -- dimension of a bathroom must not be larger than that of a kitchen
    let filter2_comb3 = filter (\(_,_,z,p) -> fst(p) <= fst(z) && snd(p) <= snd(z)) filter_comb3
    -- Remove redundant tuples whose total area is same
    let new_comb3 = redundant3 filter2_comb3 [] num_bedroom num_hall num_kitchen num_bathroom

    -- Combine previous and garden dimensions to form combination tuple: (bedroom, hall, kitchen, bathroom, garden)
    let comb4 = combinations4 new_comb3 poss_area_garden
    -- Keep only those tuples whose total area is less than or equal to given area
    let filter_comb4 = filter (\(x,y,z,p,q) -> fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen+fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden <= area) comb4
    -- Remove redundant tuples whose total area is same
    let new_comb4 = redundant4 filter_comb4 [] num_bedroom num_hall num_kitchen num_bathroom num_garden
    
    -- Combine previous and balcony dimensions to form combination tuple: (bedroom, hall, kitchen, bathroom, garden, balcony)
    let comb5 = combinations5 new_comb4 poss_area_balcony
    -- Keep only those tuples whose total area is less than or equal to given area
    let filter_comb5 = filter (\(x,y,z,p,q,r) -> fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen+fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden+fst(r)*snd(r)*num_balcony <= area) comb5
    -- Remove redundant tuples whose total area is same
    let new_comb5 = redundant5 filter_comb5 [] num_bedroom num_hall num_kitchen num_bathroom num_garden num_balcony
    
    -- Find the maximum area possible from the given dimension combinations
    let answer = maximumArea new_comb5 num_bedroom num_hall num_kitchen num_bathroom num_garden num_balcony
    
    -- Keep only those combinations whose total area is equal to maximum area (answer)
    let output = filter (\(x,y,z,p,q,r) -> fst(x)*snd(x)*num_bedroom+fst(y)*snd(y)*num_hall+fst(z)*snd(z)*num_kitchen+fst(p)*snd(p)*num_bathroom+fst(q)*snd(q)*num_garden+fst(r)*snd(r)*num_balcony == answer) new_comb5
    
    -- If no such combination exists, then print error
    if null output then do
        putStrLn "No design possible for the given constraints"
    -- Else, print the dimension details of the first combination with maximum area
    else do
        let (x,y,z,p,q,r) = output!!0
        putStrLn("Bedroom: " ++ show(num_bedroom) ++ " " ++ show(x))
        putStrLn("Hall: " ++ show(num_hall) ++ " " ++ show(y))
        putStrLn("Kitchen: " ++ show(num_kitchen) ++ " " ++ show(z))
        putStrLn("Bathroom: " ++ show(num_bathroom) ++ " " ++ show(p))
        putStrLn("Garden: " ++ show(num_garden) ++ " " ++ show(q))
        putStrLn("Balcony: " ++ show(num_balcony) ++ " " ++ show(r))
        putStrLn("Unused Space: " ++ show(area-answer))