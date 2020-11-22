-- Importing required libraries
import Data.List

-- Teams information
teams = ["BS", "CM", "CH", "CV", "CS", "DS", "EE", "HU", "MA", "ME", "PH", "ST"]
-- Match time information
times = [("1-12-2020","9:30"),("1-12-2020","7:30"),("2-12-2020","9:30"),("2-12-2020","7:30"),("3-12-2020","9:30"),("3-12-2020","7:30")]

-- Number of teams
numTeams = length teams
-- Number of matches
maxMatches = numTeams `div` 2

-- Random seed used for shuffling list
seed = 1000
-- Shuffled list of teams
shuffledTeams = (permutations teams)!!seed

-- First half of shuffled list
firstHalfShuffledTeams = take maxMatches shuffledTeams
-- Second half of shuffled list
secondHalfShuffledTeams = drop maxMatches shuffledTeams
-- Create fixtures by combining first half of teams and second half of teams
fixtures = zip firstHalfShuffledTeams secondHalfShuffledTeams

-- Prints match information in format: Team1 vs Team2     Date     Time
writeDetails :: Int -> IO()
writeDetails n = putStrLn ((fst(fixtures!!n))++" vs "++(snd(fixtures!!n))++"     "++(fst (times!!n))++"     "++(snd(times!!n)))

-- Calls writeDetails for each match i in range [0,5] recursively
fixtureHelper :: Int -> IO()
-- If i equal to maxMatches, return
-- Else call fixtureHelper recursively for i+1
fixtureHelper i = if i == maxMatches then return() else do
    writeDetails i
    fixtureHelper (i+1)

-- Prints fixture details
fixture :: String -> IO()
-- Base case for fixture: When argument is "all", print all fixtures details
fixture "all" = do
    fixtureHelper 0
-- Prints fixture details of specified team
fixture team = do
    -- If team exists, then write fixture details of that team
    -- Else print error 
    case elemIndex team shuffledTeams of
        Just id -> if id < maxMatches then writeDetails id else writeDetails (id-maxMatches)
        Nothing -> putStrLn "Team does not exist!"

-- Prints next match details given date and time
nextMatch :: Int -> Float -> IO()
nextMatch day time = do
    -- Invalid case for day
    if day < 1 || day > 31 then
        putStrLn "Invalid day!"
    -- Invalid case for time
    else if time < 0 || time > 23.59 then
        putStrLn "Invalid time!"
    -- Print details of next match depending on day and time
    else
        case day of
            1 -> if time <= 9.5 then writeDetails 0 else if time <= 19.5 then writeDetails 1 else writeDetails 2
            2 -> if time <= 9.5 then writeDetails 2 else if time <= 19.5 then writeDetails 3 else writeDetails 4
            3 -> if time <= 9.5 then writeDetails 4 else if time <= 19.5 then writeDetails 5 else putStrLn("No match ahead!")
            -- All matches are finished
            otherwise -> putStrLn("No match ahead!")