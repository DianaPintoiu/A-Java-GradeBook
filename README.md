# Object-Oriented Programming Project: GradeBook

## 1. Catalog Class

In the `Catalog` class, we maintain an `ArrayList` of courses, and the catalog is designed as a singleton to be initialized only once using the `getInstance` method. There are two methods available: `addCourse` and `removeCourse`, which perform as their names suggest.

The `Catalog` class also implements the `Subject` interface, which is used for the Observer design pattern. It needs to implement the following methods:
- `addObserver` adds a new observer to the list.
- `removeObserver` removes an observer.
- `notifyObservers` updates observers with new grades using the method from the `observers` interface.

## 2. User Classes: Student, Parent, Teacher, Assistant

All the classes, including `Student`, `Parent`, `Teacher`, and `Assistant`, inherit from the `User` class. These classes have constructors to set the first name and last name, and they are initialized through the `UserFactory`. The `getUser` method in the `UserFactory` class takes a `type` parameter and calls the appropriate constructor. The `Student` class also contains two fields, `father` and `mother,` which can be set using setter methods.

## 3. Grade Class

The `Grade` class has fields for `partialScore` and `examScore` (both of type `double`), `student` (of type `Student`), and a `course` (of type `String`). It provides getter and setter methods for these fields. The `getTotal` method calculates the total score by summing `partialScore` and `examScore`.

Additionally, the `Grade` class implements two interfaces, `Clonable` and `Comparable`. In the `Comparable` interface, we have written the `compareTo` function, which compares grades based on the highest score. In the `Clonable` interface, we have the `clone` function.

## 4. Group Class

The `Group` class includes an `assistant`, an `id`, and a `student` comparator. It has two constructors: one for all three fields and another for only the first two. In the constructor with the comparator, the method for comparing two students based on their last name followed by their first name is provided.

## 5. Course Class

The `Course` class represents a course and includes fields such as the course `name`, `strategy`, `teacher`, `assistants`, `grades`, and a `HashMap` with a key of `String` and a value of a `Group` object. The Builder design pattern is used for the constructor, and we have an abstract inner class, `CourseBuilder`, for initializing the course fields. There is also an abstract `build` method.

The `PartialCourse` and `FullCourse` classes extend the `Course` class and provide a `PartialCourseBuilder` and `FullCourseBuilder`, respectively. These classes define the `build` method. Both classes also implement the `getGraduatedStudents` method from the `Course` class.

The `PartialCourse` class requires a total grade higher than 5, while the `FullCourse` requires a partial score higher than 3 and an exam score higher than 2.

The `Course` class offers various getter and setter methods and includes functions like `addAssistant`, `addStudent`, `addGroup`, `addGroup`, `getGrade`, `addGrade`, `getAllStudents`, and `getAllStudentGrades`.

## 6. Test Class

The `Test` class reads data from a `catalog.json` file and populates the catalog. In the second part, it reads data from a JSON file and adds it to the corresponding data structures in the `ScoreVisitor` class. Finally, the `Test` class includes tests to run various methods.

## 7. StudentPage1 Class

The `StudentPage1` class is responsible for creating a graphical user interface for the student's first page. When you use the `StudentPage1` constructor in the `Test` class, it takes the student's name as an argument and displays information about the student's courses. You can see the course professor, assigned assistants, the student's assigned assistant, partial scores, and exam scores when you click on a course.

