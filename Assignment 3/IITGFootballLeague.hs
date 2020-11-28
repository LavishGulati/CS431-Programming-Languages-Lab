-- Importing required libraries
import Data.List
import Data.Maybe
import System.Directory
import System.IO.Unsafe
import Data.IORef
import System.Random

-- Initialize random utility functions
random_list = unsafePerformIO(newIORef [])
random_gen = readIORef random_list
random_store val = writeIORef random_list val

-- Match time information
times = [("1-12-2020","9:30 AM"),("1-12-2020","7:30 PM"),("2-12-2020","9:30 AM"),("2-12-2020","7:30 PM"),("3-12-2020","9:30 AM"),("3-12-2020","7:30 PM")]

-- Number of matches
maxMatches = length times
numTeams = maxMatches*2

-- Prints match information in format: Team1 vs Team2     Date     Time
writeDetails n fixtures = putStrLn ((fst(fixtures!!n))++" vs "++(snd(fixtures!!n))++"     "++(fst (times!!n))++"     "++(snd(times!!n)))

-- Calls writeDetails for each match i in range [0,5] recursively
-- If i equal to maxMatches, return
-- Else call fixtureHelper recursively for i+1
fixtureHelper i fixtures = if i == maxMatches then return() else do
    writeDetails i fixtures
    fixtureHelper (i+1) fixtures

-- Prints fixture details
-- Base case for fixture: When argument is "all", print all fixtures details
fixture "all" = do
    -- Initialize random generator
    gen <- newStdGen
    -- Get new seed from random generator
    let seed = fst(randomR (1, 100000) gen)
    -- Shuffle teams according to the random seed
    let shuffledTeams = (permutations ["BS", "CM", "CH", "CV", "CS", "DS", "EE", "HU", "MA", "ME", "PH", "ST"])!!seed
    -- First half of shuffled list
    let firstHalfShuffledTeams = take maxMatches shuffledTeams
    -- Second half of shuffled list
    let secondHalfShuffledTeams = drop maxMatches shuffledTeams
    -- Create fixtures by combining first half of teams and second half of teams
    let fixtures = zip firstHalfShuffledTeams secondHalfShuffledTeams
    -- Store shuffledTeams as a reference variable
    random_store shuffledTeams
    -- Print all fixtures
    fixtureHelper 0 fixtures

-- Prints fixture details of specified team
fixture team = do
    -- Fetch shuffledTeams from stored reference
    shuffledTeams <- random_gen
    -- First half of shuffled list
    let firstHalfShuffledTeams = take maxMatches shuffledTeams
    -- Second half of shuffled list
    let secondHalfShuffledTeams = drop maxMatches shuffledTeams
    -- Create fixtures by combining first half of teams and second half of teams
    let fixtures = zip firstHalfShuffledTeams secondHalfShuffledTeams
    -- If shuffledTeams is null, throw error
    -- Else if team exists, then write fixture details of that team
    -- Else print error
    if null shuffledTeams then
        putStrLn "Fixtures not initialized!"
    else
        case elemIndex team shuffledTeams of
            Just id -> if id < maxMatches then writeDetails id fixtures else writeDetails (id-maxMatches) fixtures
            Nothing -> putStrLn "Team does not exist!"

-- Prints next match details given date and time
nextMatch day time = do
    -- Fetch shuffledTeams from stored reference
    shuffledTeams <- random_gen
    -- First half of shuffled list
    let firstHalfShuffledTeams = take maxMatches shuffledTeams
    -- Second half of shuffled list
    let secondHalfShuffledTeams = drop maxMatches shuffledTeams
    -- Create fixtures by combining first half of teams and second half of teams
    let fixtures = zip firstHalfShuffledTeams secondHalfShuffledTeams
    -- Invalid case for day
    if null shuffledTeams then
        putStrLn "Fixtures not initialized!"
    else if day < 1 || day > 31 then
        putStrLn "Invalid day!"
    -- Invalid case for time
    else if time < 0 || time > 23.99 then
        putStrLn "Invalid time!"
    -- Print details of next match depending on day and time
    else
        case day of
            1 -> if time <= 9.5 then writeDetails 0 fixtures else if time <= 19.5 then writeDetails 1 fixtures else writeDetails 2 fixtures
            2 -> if time <= 9.5 then writeDetails 2 fixtures else if time <= 19.5 then writeDetails 3 fixtures else writeDetails 4 fixtures
            3 -> if time <= 9.5 then writeDetails 4 fixtures else if time <= 19.5 then writeDetails 5 fixtures else putStrLn("No match ahead!")
            -- All matches are finished
            otherwise -> putStrLn("No match ahead!")