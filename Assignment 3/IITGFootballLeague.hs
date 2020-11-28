-- Importing required libraries
import Data.List
import Data.Maybe
import System.Directory
import System.IO.Unsafe
import Data.IORef
import System.Random

listg :: IORef [[Char]]
listg = unsafePerformIO (newIORef [])

readg :: IO [[Char]]
readg = readIORef listg

writeg :: [[Char]] -> IO ()
writeg value = writeIORef listg value

-- Match time information
times = [("1-12-2020","9:30"),("1-12-2020","7:30"),("2-12-2020","9:30"),("2-12-2020","7:30"),("3-12-2020","9:30"),("3-12-2020","7:30")]

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
    g <- newStdGen
    let temp = randomR (1, 100000) g
    let newSeed = fst(temp)
    let shuffledTeams = permutations ["BS","CM","CH","CV","CS","DS","EE","HU","MA","ME","PH","ST"]!!newSeed
    -- First half of shuffled list
    let firstHalfShuffledTeams = take maxMatches shuffledTeams
    -- Second half of shuffled list
    let secondHalfShuffledTeams = drop maxMatches shuffledTeams
    -- Create fixtures by combining first half of teams and second half of teams
    let fixtures = zip firstHalfShuffledTeams secondHalfShuffledTeams

    writeg shuffledTeams
    fixtureHelper 0 fixtures

-- Prints fixture details of specified team
fixture team = do
    shuffledTeams <- readg
    -- First half of shuffled list
    let firstHalfShuffledTeams = take maxMatches shuffledTeams
    -- Second half of shuffled list
    let secondHalfShuffledTeams = drop maxMatches shuffledTeams
    -- Create fixtures by combining first half of teams and second half of teams
    let fixtures = zip firstHalfShuffledTeams secondHalfShuffledTeams
    -- If team exists, then write fixture details of that team
    -- Else print error 
    case elemIndex team shuffledTeams of
        Just id -> if id < maxMatches then writeDetails id fixtures else writeDetails (id-maxMatches) fixtures
        Nothing -> putStrLn "Team does not exist!"

-- Prints next match details given date and time
nextMatch day time = do
    shuffledTeams <- readg
    -- First half of shuffled list
    let firstHalfShuffledTeams = take maxMatches shuffledTeams
    -- Second half of shuffled list
    let secondHalfShuffledTeams = drop maxMatches shuffledTeams
    -- Create fixtures by combining first half of teams and second half of teams
    let fixtures = zip firstHalfShuffledTeams secondHalfShuffledTeams
    -- Invalid case for day
    if null shuffledTeams then
        putStrLn "fixtures not initalized"
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