Easy asynchronous asserts with JUnit & Hamcrest
=======

## Usage

These matchers are available via Maven Central. Add this to your pom.xml:

```xml
<dependency>
  <groupId>com.github.grantwest.eventually</groupId>
   <artifactId>hamcrest-eventually-matchers</artifactId>
   <version>0.0.1</version>
</dependency>
```

Lets say another thread is supposed to add an element to a collection:
```java
Collection<String> listOfNames; //Collection other thread is adding names to
assertThat(listOfNames, eventually(hasItem("Grant")));
```

To test a value we use a lambda:
```java
boolean b = false; //Some other thread will set b to true
assertThat(() -> b, eventuallyEval(is(true)));
```
