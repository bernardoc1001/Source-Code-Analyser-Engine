# Blog: Source-Code-Analyser-Engine

**Bernard O'Connor**

[TOC]: #

## Table of Contents
- [Blog #1 (1/11/2017)](#blog-1-1112017)
- [Blog #2 (19/11/2017)](#blog-2-19112017)
- [Blog #3 (21/11/2017)](#blog-3-21112017)
- [Blog #4 (25/11/2017)](#blog-4-25112017)





## Blog #1 (1/11/2017)

#### What I've Done:
My project, the Source Code Analyser Engine, has been approved.

I've met with my Project Supervisor, Dr David Sinclair, to discuss what needs to be done with my Functional Specification, which is the next deliverable for the project. 

I have started work on the Functional Specification for the project. I've talked about the business context for the project, and its future open source nature.

#### What I am Currently Doing:
I am currently fleshing out the User Characteristics and Use Case Scenario sections of the Functional Specification. Once I work out exactly what the User will wish to achieve with my system, it will be easier to design the required functionality to achieve these goals. 


#### What I Will Do:
Next I will work on the Functional Requirements for the Functional Specification. Also before my next Supervisor meeting I will try to create  an early mock up of the UI for the supporting website that I will be building for the Source Code Analyser Engine Project.




## Blog #2 (19/11/2017)
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


## Blog #3 (21/11/2017)


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


## Blog #4 (25/11/2017)


#### What I've Done:
I finished and submitted the Functional Specification yesterday.

#### What I am Currently Doing:
Right now in college we are about to go into week 11 of the first Semester. My focus right now is to finish of the last 2 assignments that I have for the semester as soon as I can.

#### What I Will Do:
As outlined in the below Gantt Chart, before I break to focus on the first semester exams I would like to try and get the supporting website up and running to a very basic degree.
Mainly just to get the webserver running and to ensure that the routing between blank pages are working. Then I will break for the exams before coming back and getting stuck into the heart of the project.

![gantt%20dates](images/gantt%20dates.png)
![gantt%20chart](images/gantt%20chart.png)