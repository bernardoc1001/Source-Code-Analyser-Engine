# Blog: Source-Code-Analyser-Engine

**Bernard O'Connor**

[TOC]: #

## Table of Contents
- [Blog Post 1 (1/11/2017)](#blog-post-1-1112017)
- [Blog Post 2 (19/11/2017)](#blog-post-2-19112017)
- [Blog Post 3 (21/11/2017)](#blog-post-3-21112017)
- [Blog Post 4 (25/11/2017)](#blog-post-4-25112017)
- [Blog Post 5 (29/01/2018)](#blog-post-5-29012018)
- [Blog Post 6 (05/02/2018)](#blog-post-6-05022018)
- [Blog Post 7 (12/02/2018)](#blog-post-7-12022018)





## Blog Post 1 (1/11/2017)

#### What I've Done:
My project, the Source Code Analyser Engine, has been approved.

I've met with my Project Supervisor, Dr David Sinclair, to discuss what needs to be done with my Functional Specification, which is the next deliverable for the project. 

I have started work on the Functional Specification for the project. I've talked about the business context for the project, and its future open source nature.

#### What I am Currently Doing:
I am currently fleshing out the User Characteristics and Use Case Scenario sections of the Functional Specification. Once I work out exactly what the User will wish to achieve with my system, it will be easier to design the required functionality to achieve these goals. 


#### What I Will Do:
Next I will work on the Functional Requirements for the Functional Specification. Also before my next Supervisor meeting I will try to create  an early mock up of the UI for the supporting website that I will be building for the Source Code Analyser Engine Project.




## Blog Post 2 (19/11/2017)
It has been a little while since my last blog post. The various assignments and working on the functional spec has kept me on my toes.

#### What I've Done:
I've fleshed out the Use Case Scenarios and the Functional Requirements some more.

While doing so I've come to a better understanding of my design. Before I was picturing developing two versions of my project, a web-process and an offline process.
While working on my Functional Requirements, I have realised that instead I should be making a java Library. The users of the project (who are all developers) will then make calls to the API of this library from their own web-processes / offline processes.
I have gone back to previous sections of my Functional Spec to make sure that this slight change of scope has been accounted for.


I also met with my supervisor last Thursday (16/11/2017). We discussed how much I should focus my functional specification on my main project vs example uses of my project, and about a sequence diagram I made, shown below.
![WIP sequence diagram](./images/Sequence%20Diagram%20WIP.png)

#### What I am Currently Doing:
I am working on getting as much of the functional spec done for the next supervisor meeting on Tuesday, so I can recieve feedback before the due date on friday. Currently I am working on again adding more detail to the functional requirements and making some initial diagrams for the high level design.



#### What I Will Do:
Finish the Functional Specification for Friday.


## Blog Post 3 (21/11/2017)


#### What I've Done:
I had a meeting today with my supervisor to discuss the diagrams I have made since our last meeting. He was happy with them, though advised me to keep my state diagram for the technical manual.

###### \[Work In Progress\] Sequence Diagram for the SCAE Library
![Sequence%20Diagram%20WIP%20v2](images/Sequence%20Diagram%20WIP%20v2.png)

###### \[Work In Progress\] System Architecture Diagram
![System%20Architecture%20WIP_1](images/System%20Architecture%20WIP_1.png)

###### \[Work In Progress\] State Diagram Lexical Analyser
![State%20Diagram%20Lexical%20Analyser%20WIP_1](images/State%20Diagram%20Lexical%20Analyser%20WIP_1.png)


I also finalised the scope of my project, which I then discussed with my supervisor. At the heart of my project is the Source Code Analyser Engine library. What I've added to the scope is then a web-application that not only will host downloads and documentation for the library, but it will also have a public web API that other projects may easily point to.
These other projects will send a request with the source code to be analysed, and a string representation of the style rulebook and corresponding tokens to be used. This string representation can either be a URL to a raw version of a rulebook, the string may be the rulebook itself, or it may be an enum to point to one of my pre-approved rulebooks.

###### Rough Diagram to highlight new scope / full project architecture
![Project%20Rough%20Scope_1](images/Project%20Rough%20Scope_1.png)

#### What I am Currently Doing:
I am currently adding the final touches to the diagrams and reflecting the changes to the scope of the project in my User Scenarios and my Functional Requirements


#### What I Will Do:
Create a Gantt Chart and finish the Functional Specification for Friday.


## Blog Post 4 (25/11/2017)


#### What I've Done:
I finished and submitted the Functional Specification yesterday.

#### What I am Currently Doing:
Right now in college we are about to go into week 11 of the first Semester. My focus right now is to finish of the last 2 assignments that I have for the semester as soon as I can.

#### What I Will Do:
As outlined in the below Gantt Chart, before I break to focus on the first semester exams I would like to try and get the supporting website up and running to a very basic degree.
Mainly just to get the webserver running and to ensure that the routing between blank pages are working. Then I will break for the exams before coming back and getting stuck into the heart of the project.

![gantt%20dates](images/gantt%20dates.png)
![gantt%20chart](images/gantt%20chart.png)


## Blog Post 5 (29/01/2018)

#### What I've Done:
It is now Mondary of Week 1 of the Second Semester. Since the last update, I had not worked on the project before the exams as I had hoped.
Since the exams though, I have more or less caught up with where I plan to be in the Gantt Chart.
I set up an issues board on gitlab to keep track of which tasks I'm doing, referencing the issue number on each commit I make.

![issue_board_29-01-2018](images/issue_board_29-01-2018.png)


Then I setup setup the webserver using lein, the Clojure and Clojurescript project management tool.

Then I create a base layout of the website with a side navigation bar and defined routes for the pages in the backend handler.

Finally I created an initial code submission page for a user to enter both their code and select which rulebook to use. This page still needs to retrieve rulebooks and to then make a post request to the future Web-API of the project.

![initial-code-submission-page.png](images/initial-code-submission-page.png)

#### What I am Currently Doing:
I am about to start making a dummy Web-API that will have a similar structure to the final product. I want to create this to cement what kind of requests I'll need the library to be able to handle, and to test sending and recieving information to this RESTful API.



#### What I Will Do:
The next thing I need to do is organise a supervisor meeting to discuss what I have done so far and what should be my next priority. Per earlier discussions in our previous meeting, after I finish the basics of this website (after I finish my current task), the next step would be to start working on the main Source Code Analyser Library itself, and to leave development of the website for a while.




## Blog Post 6 (05/02/2018)

#### What I've Done:
It is now Monday of Week 2 of the Second Semester.

The first thing I did this sprint is to create an API endpoint for the Source Code Analyser Engine, that just returned the request that was made to it as a placeholder until I get the library processing and returning data. I had some issues with the dependencies of some of the middleware modules whose dependencies conflicted with the version of Clojure I was using, forcing me to rollback to an earlier version. Also I have currently disabled Anti-Forgery Token checks, as currently I don't want users to have to be logged in to access the SCAE API.

Then I looked at how I was managing my dependencies for the website sub-project. Initially I had it where any dev working on this project would have to manually download and install the dependencies in the resources folder.
After looking into a couple package-management tools such as bower and npm, I've decided to go with yarn, which works similarly to npm. The reason I chose yarn over npm was that yarn allowed me to easily define where I wanted my node_modules folder with my depenencies to be installed, which was a requirement of mine as I couldn't go with npm's default of the root folder of the project, instead I install them to the resources folder with the following command (assuming I am in the src/scae-website directory)
```
 yarn --modules-folder ./resources/public/node_modules/
```
This automatically installs all of the dependencies defined in the package.json to the specified directory.

Next I spent a day or two reviewing some of the later chapters of the [Clojure For The Brave And True](https://www.braveclojure.com/clojure-for-the-brave-and-true/) book, specifically the chapters dealing with clojure macros (Which allow you to access the AST of the Clojure Program itself as it runs and to manipulate said tree) and the concurrency in clojure chapters. I felt that familiarising myself with these concepts now would be beneficial as I move forward with the project.

Finally I created the Source Code Analyser Engine Library subproject (src/scae-library). Currently this library returns the value of any arguments passed to it. I then looked into installing the library locally, which you do by running
```
lein install
```
when in the src/scae-library directory. This compiles the library and installs it to the .m2 directory in the user's home directory, where leinigen (the build tool) stores its downloaded dependencies.
I then added the scae-library as a dependency for the scae-website project, and in the endpoint I created earlier I call the library's dummy function that returns the arguments passed to it, to ensure that I both compiled and imported the library correctly, which I did.


#### What I am Currently Doing:
Currently I am working on the Engine Module in the scae-library, which is what will keep track of and call the different submodules needed to analyse the code.

I am also reviewing what was discussed today with my Supervisor, David Sinclair, about which tasks I should prioritise next.


#### What I Will Do:
The tasks that we discussed that should be accomplished for next week are:
* Complete the Engine module
* Create the Tokeniser module
* Investigate the data structures required for the analysis (determine if Abstract Syntax Tree is necessary, and if so if there are any clojure library's that I can leverage for this processing)
* Begin work on the Symbol Table and the possible Abstract Syntax Tree

## Blog Post 7 (12/02/2018)

#### What I've Done:
It's now Monday of week 3.

In the last sprint I finished the creating the Engine module task,
the tokeniser task (plus tests), and investigating which data structures
need to be used (see issue #19 for the result). I am carrying forward
the tasks for the Symbol Table and Abstract Syntax Tree into the Sprint 4.

The tokeniser task proved to be challenging. I knew going in that I would
have the user input a token definitions list consisting of regexes for
the different tokens, along with the code to be analysed. I'll explain
my solution to this problem, which will be one line of clojure, in detail below
as clojure code can be hard to decipher without learning the language.
The rest of the tokeniser.clj contains a lot of collection (a data-structure)
manipulation that would take too long to explain in detail here.

The challenging
part was to perform these regex searches while preserving the ordering of
the tokens (if I iteratively/recursively did regex searches for each token,
I would lose the ordering of each token). I solved this in the following code fragment
```
(clojure.string/join "|" (map :regex (vals (:tokens tokens))))
```
To read the above code let us go from right to left. `(:tokens tokens)`
extracts the value of the key "tokens" from the following hash-map,
 ```
 {:tokens {:NEGATE {:regex "~", :type :token},
           :COLON {:regex ":", :type :token},
           :ID {:regex "(?:[a-zA-Z])+(?:[0-9]|_|[a-zA-Z])*", :type :token}}
 ```

where the value is a hash-map containing the keys for the tokens, and an inner
hash-map for each token containing the regex string and what kind of token
this is (either a regular token or a skip token).

This hash-map is passed to the vals function `(vals (:tokens tokens))`. This
creates a collection (a list) of the values for each key in the hash-map,
e.g `({:regex "~", :type :token}, {:regex ":", :type :token}, {:regex "(?:[a-zA-Z])+(?:[0-9]|_|[a-zA-Z])*", :type :token})`

We then pass this list into the following function `(map :regex (vals (:tokens tokens)))`.
The map function takes as parameters a function (in this case the keyword :regex
is being treated as a function that will extract the value of the key from a map)
and the list (referred to as a collection) that we've made previously. The map
function applies the given function to every element in the given collection, and returns
a LazySequence (a non-fully realised list) of every result. So in our example
we extract the value of the regex key (which is a string) from every single hash-map in the given list,
and put it into a lazy sequence.

Finally we get to the clojure string join method
`(clojure.string/join "|" (map :regex (vals (:tokens tokens))))` which combines
every string in the LazySequence together, separated by "|", which in regexes is the
OR operator. In subsequent lines of code, I use this string to perform one
single regex search for every kind of token that we have defined. This solves
the problem that we faced with trying to preserve the ordering of the regexes.

The reason why I chose to do my project in Clojure is highlighted by the above.
You are able to do a tremendous amount of data manipulation with very succinct code,
 which will be useful throughout this project as I perform the Source Code Analysation,
 manipulating various kinds of data structures such as Syntax Tables and Abstract Syntax Trees.

#### What I am Currently Doing:
I am just about to move onto the "Create A Symbol Table" task, which will
allow me to keep track of various things such as scope.

#### What I Will Do:
The next major task that I want to complete in Sprint 4 is to Create an
Abstract Syntax Tree.

