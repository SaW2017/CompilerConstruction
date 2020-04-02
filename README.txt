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
