# HW13

### Package 
To create .jar file run: `mvn package`.
Jar file is created in `<project-root>/target/HW13-GP.jar`

### Run
`java -jar target/HW13-GP.jar <map> <max_generations> <population_size> <min_fitness> <output_path>`

Example:
  `java -jar target/HW13-GP.jar 13-SantaFeAntTrail.txt 100 1000 89 best_sol.txt`
  
If no arguments are provided, default values are used:
- mapPath = "13-SantaFeAntTrail.txt"
- maxIter = 100
- populationSize = 1000
- minFitness = 89
- dumpBestPath = "best.txt"

---
### Best solution
Best solution is also serialized into file given by path in a form of a tree 
- indentation represent depth of the node in the tree
- children are given under the parent with additional one level of indentation.

Example:
```
  IfFoodAhead
    Prog3
      Prog2
        Move
        Right
      Left
      Move
    Left 
```
 - Node 'IfFoodAhead' has two children 'Prog3' and 'Left'. 
 - Node 'Prog3' has three children 'Prog2', 'Left' and 'Move'. 
 - Node 'Prog2' has two children 'Move' and 'Right'. 

---
### Without maven
Alternatively if you don't want to run maven you can:
- compile with javac:
  `javac -cp src/main/java src/main/java/hr/fer/zemris/optjava/dz13/AntTrailGA.java`
- run:
  `java -cp src/main/java/ hr.fer.zemris.optjava.dz13.AntTrailGA 13-SantaFeAntTrail.txt 100 1000 89 best_sol.txt`


