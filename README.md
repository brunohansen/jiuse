# iMove

1. [Overview](#overview)
2. [Installation](#installation)
3. [Available Data](#available-data)
4. [Evaluation Setup](#evaluation-setup) 

## Overview

iMove is available at [releases page](https://github.com/brunohansen/iMove/releases/tag/v1.0.0), it's implemented in Java (1.8) as an Eclipse plug-in (Neon 3 Release 4.6.3). iMove not only allows the user to calculate the metrics and correlations but also to evaluate, in different ways, Move Method suggestions. iMove evaluates the effect of each refactoring suggestion on software quality, helping the user to determine whether refactoring should or not be applied.

### Metrics

iMove takes the source code of the software under development as input, allowing the calculation of metrics and evaluation of the Move Methods refactorings. To calculate a metric, the user selects the method or class and chooses the metric to be calculated from the menu ([Figure 1](#metrics-menu)). After this selection, iMove calculates the desired metric using one of the Equations CAMC and IUC for classes and one of the Equations MDC and MUC for methods.

##### Metrics Menu
![Metric Menu](https://raw.githubusercontent.com/wiki/brunohansen/iMove/metricMenu.png "Figure 1")

### Move Method

To evaluate Move Method refactorings, iMove allows the software engineer to indicate a move suggestion in four different ways ([Figure 2](#move-method-menu)). The user can manually select the method to be moved, and its target class, input a single suggestion, or import suggestions from a file or folder - a set of Move Method suggestion files. A suggestion file contains a set of Move Method suggestions and should have the syntax presented in [Listing 1](#move-method-suggestions-file), this type of file can be generated by almost any type of Eclipse Move Method plug-in, including [JDeodorant](https://github.com/tsantalis/JDeodorant) and [JMove](https://github.com/aserg-ufmg/jmove/). Then, iMove evaluates the Move Method suggestion. If iMove validates the Move Method, the plug-in allows the user to inspect the recommended Move Method. It then offers the user to preview of its effect in the source code, or to apply the refactoring, using the Eclipse Move Method Assistant ([Figure 3](#move-method-assistant)).

##### Move Method Menu
![Move Method Menu](https://raw.githubusercontent.com/wiki/brunohansen/iMove/moveMenu.png "Figure 2")

##### Move Method Suggestions File
```
    org...Request::sortRequest(MaxCore):Request         org...max.MaxCore  
    org...Description::apply(Statement, Timeout):void   org...Timeout  
    org...Result::printFooter(TextListener):void        org...TextListener  

Syntax: pkg.SourceClass::method(Parameters):Return  pkg.TargetClass
```

##### Move Method Assistant
![Move Method Assistant](https://raw.githubusercontent.com/wiki/brunohansen/iMove/moveMethodAss.png "Figure 3")

### iMove View

To evaluate a set of Move Method recommendations, the plug-in shows the evaluation results in a view ([Figure 4](#inspect-view)). In this view, the user can see if a recommendation was validated by iMove or not and can also inspect the method or the target class. Moreover, the user can apply or preview the refactoring effect in the source code, by opening the Eclipse Move Method Assistant ([Figure 3](#move-method-assistant)).

##### Inspect View
![Inspect View](https://raw.githubusercontent.com/wiki/brunohansen/iMove/iMoveView.png "Figure 4")

###

Finally, to help validate the results obtained, the iMove allows comparing a Move Method recommendation file against a Move Method suggestion file of correct Move Method refactorings.

## Installation

Save [iMover jar](https://github.com/brunohansen/iMove/releases/tag/v1.0.0) in eclipse plugins folder and restart it.

## Evaluation

### Available data

#### Modified Software and Gold Sets

A copy of modified versions of the subject software is in a diferent repository available at [iMove-Dataset](https://github.com/brunohansen/iMove-Dataset) and a copy of the gold sets is available at [Data/gold_sets](https://github.com/brunohansen/iMove/tree/master/Data/gold_sets) folder.

#### Move Method Tools

A copy of JDeodorant and JMove are available at [Tools](https://github.com/brunohansen/iMove/tree/master/Tools) folder.

#### Move Method Suggestions File

The move method suggestions files for each modified software generated by JDeodorant and JMove are available at [Data/results](https://github.com/brunohansen/iMove/tree/master/Data/results) folder. 

### Evaluation setup

1. Download and install [Eclipse](https://www.eclipse.org/downloads/packages/release/neon/3/eclipse-ide-eclipse-committers);
2. Save [iMove](https://github.com/brunohansen/iMove/releases/tag/v1.0.0) jar in eclipse plugins folder;
3. Download the [subjects softwares](https://github.com/brunohansen/iMove-Dataset/archive/master.zip), extract all and import as eclipse projects;
4. Download [iMove repository](https://github.com/brunohansen/iMove/archive/master.zip), extract it and use files located at Data/results folder to evaluate iMove. 





