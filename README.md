QA AUTOMATION TECHNICAL TEST FOR NUVOLAR WORKS

This project has been developed using This project has been developed using Java (11.0.6 version) and Maven as build and run framework.

The dependencies installed are the following:

* org.testng (7.3.0)
* org.json.simple (1.1.1)
* org.seleniumhq (selenium-java 3.141.59)

The run configuration must have the parameter '-Dtestng.dtd.http=true' included in the run line in JDK Settings->VM Options

In the path 'src/test/resources' you will find a chromedriver.exe executable.
It's for the Chrome version 87.0.4280.88. If you want to use another version change this file with the one for the version you're using. you can download it from https://chromedriver.chromium.org/downloads.

I've separated the object identification by pages, trying to use Page Object models.
I've also used json to enter some information before the test, using the TestNG annotations to make the order correct.

I have done the test just as it was specified, following the steps one by one:

1. Go to https://www.amazon.com
2. Search for "hats for men" 
3. Add first hat to Cart with quantity 2
4. Open cart and assert total price and quantity are correct
5. Search for "hats for women"
6. Add first hat to Cart with quantity 1
7. Open cart and assert total price and quantity are correct
8. Change the quantity for item selected at step 3 from 2 to 1 item in Cart
9. Assert total price and quantity are changed correctly

I have to say that, in the real world, I would never do a test like this one. In the case of the automamtion of this functionality there would be lots of classes and functions that could be reused, redone, created in different classes, etc.
If for any reason I had to cover something like this I probably split this in smaller and more atomic tests that, working together, would cover all.
Please, do not take this as a criticism, I'm just being honest. In fact, I think this exercise works perfectly well as test for candidates selection, and for that purpose it was designed/selected by you.
