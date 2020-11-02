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

### Java Class
Analyze:

-AnalyzePlugin.java is an interface for other java class which has two methodes, result as String or as Html Div.

-CountCommotsPerAuthorPlugin.java as the name of the class say it  permits to count commits per Authors, it has Result class imbricate in, it changes results of commits to htlm,

-AnalyzeResult put all result in a arrylist and have methode to display as string and html

-Analyze create list of plugins 

Cli:

-CLILauncher.java create commend (-- addPluggin, help..)

Config:

-Configuration.java git path and config for plugins

-PluginConfig.java an interface (not usable yet) which is setting Key into Value    


## Usage

### Building the project

1. clone the repository
    ```
    git clone git@gaufre.informatique.univ-paris-diderot.fr:adegorre/visulog.git
    ```
   or
    ```
    git clone https://gaufre.informatique.univ-paris-diderot.fr/adegorre/visulog.git
    ```
2. Enter the project folder
    ```
    cd visulog
    ```
3. Only if you are on a SCRIPT computer (in one of the TP rooms):
    ```
    source SCRIPT/envsetup
    ```
    This will setup the GRADLE_OPTS environment variable so that gradle uses the SCRIPT proxy for downloading its dependencies. It will also use a custom trust store (the one installed in the system is apparently broken... ).
4. run gradle wrapper (it will download all dependencies, including gradle itself)
    ```
    ./gradlew build
    ```
### Running the software

Currently, it can be run through gradle too. In order to pass program arguments, you need to pass them behind `--args`:
```
./gradlew run --args='here are my args'
```

For instance

```
./gradlew run --args='. --addPlugin=countCommits'
```

Will count the commits of each author in the current branch of the git repository present in the current folder (".").
