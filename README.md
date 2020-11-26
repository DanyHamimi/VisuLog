# Visulog

*Tool for analysis and visualization of git logs*

## Presentation

Visulog a tool for analyzing contributions from the members of a team working a a same given project hosted on a git repository. Its goal is to assist teachers for individual grading of students working as a team.

This tool can:

- compute a couple of relevant indicators such as:
  - number of lines or characters added/deleted/changed
  - number of commits
  - number of merge commits
- analyze the variations of these indicators in time: for instance sum then in a week, compute a daily average or an average in a sliding window, ...
- visualize the indicators as charts (histograms, pie charts, etc.) embedded in a generated web page.

## Already existing similar tools

- [gitstats](https://pypi.org/project/gitstats/) 

## Technical means

- The charts are generated by a third party library (maybe a Java library generating pictures, or a javascript library which dynamically interprets the data).
- The data to analyze can be obtained using calls to the git CLI. For instance "git log", "git diff --numstat", and so on.

## Architecture

Visulog contains the following modules:

- data types for storing raw data directly extracted from git history, with relevant parsers
- a generator of numerical series (for the indicators mentioned above)
- a generator of web pages
- a command line program that calls the other modules using the provided command line parameters
- a shared module for configuration object definitions

## Sometimes Exception occurred while cloning repo
- org.eclipse.jgit.api.errors.TransportException: https://gaufre.informatique.univ-paris-diderot.fr/filipsudol/visulog: cannot open git-upload-pack
- Simple fixe it's to enter: git config http.sslVerify true 

## How to run the program
./gradlew run --args='https://gaufre.informatique.univ-paris-diderot.fr/filipsudol/visulog'

- circle / bar / default with div HTML
- ./gradlew run --args='https://gaufre.informatique.univ-paris-diderot.fr/filipsudol/visulog circle'
- ./gradlew run --args='https://gaufre.informatique.univ-paris-diderot.fr/filipsudol/visulog bar'