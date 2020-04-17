CA1 Assignment from:
Daniel Leustik, Klaus Volk and Sarah Wachter

Zip contains: 
- package "impl"
- package "test.backend.sm"

If you use your own build-dist.xml then please Change in build-dist.xml:  
<!-- list of Java test classes -->
  to:
  <property name="testclasses" value="Test1,Test2,Test3,Test4,Test5,Test6,Test7,Test8,Test9,Test10,Test11"/>

compile & run with:
ant -buildfile build-dist.xml "eval-all"

Please look for code coverage at: 
Assignment CA1.pdf.pdf



CA2 Assignment:

Needed Reference: 
https://javacc.github.io/javacc/tutorials/token-manager.html
https://www.cs.purdue.edu/homes/hosking/javacc/doc/lookahead.html
https://ant.apache.org/manual/Tasks/javacc.html

build.xml:
change javacc-Home Path

before running - delete old compiled classes, otherwise you`ll get an error because of same named files.
if an error occurs after building, then there isn`t a package set in the specified files, please
set it manually

run different examples with:
ant -buildfile build-dist-mj.xml c21
ant -buildfile build-dist-mj.xml c22
ant -buildfile build-dist-mj.xml c23
(Parser is top-down from general (program) to detail (letter "a...z")



