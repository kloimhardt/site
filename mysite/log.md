# 1
## 1
Here you see the musical scale. The black dots are the notes from A3 to A5. Thy span two octaves.
https://www.youtube.com/watch?v=SArAehjpMyk
/Users/kloimhardt/klmtemp/site/mysite/audio/violinA3A5.m4a

## 2
From A3 to A4 is one octave, and from A4 to A5 is also an octave. This is indicated by the red lines.
You look at this and find it quite natural that the length of the red lines is equal. How could that be otherwise, you think.
You might have guessed that I chose this example because this now gives me the excuse to resort back to the old greeks. I thought this gives a bite of splendor to my presentation.

## 3
It was the Pythagoreans who found out that to play an octave, the ratio of the cords must not be equal. They found out that the ratio of the cords for the octave must be two to one.

From today's perspective this becomes clear if we draw the notes of the musical scale according to their frequency. Then the distance from A3 to A4 is half the distance from A4 to A5.

## 4
That is: the note A3 vibrates at the frequency of 220Hz. If you double those 220 cycles per second, you land at 440 Hz which is the frequency of the note A4. A5 is accordingly at 880Hz.

## 5
What Pythagoras did not know is that the notes of the muscal scale follow the log2 function. Log2 is the red curve you see, it is the curve that connects the dots.

## 6
The name log2 is the name we give this curve today. How the Indian monk Virasena called it, I do not know. He lived in the year 800 of our common era, that is some one thousend years after Pythagoras. It is this monk Virasena who produced the first tables of log2.

# 2a
So the main protagonist I'd like to present to you is log2.

# 2b
It is easy to quote the number of years in history. A bit trickier is the presentaion of the numbers in the actual formula that fits the notes. The trick I have chosen to present the numbers is a special pictorial form.

To draw the log2 curve fitting the notes, I was using the following formula: log2 of x divided by 220 times 7.

You might want to take asunder the formula to get a deeper understanding. --P4--

the independent variable I call x stands for the frequency
It is divided by 220
Of this value, the log2 is determined. In ancient times, it was the indian monks who provided the tables to do this.

Then the result is multiplied by seven. This multiplication by 7  means that the frequency is doubled every 7th step.

If you hear the word octave, the number eight is what springs to mind. But if you expend the effort, you will realize that an octave is made of seven steps.
So you start at A4, which is at 440 Hertz. Then you go upwards 7 steps: 1 2 3 4 5 6 7. You land at A5 which is 880 Hertz. Double the frequency every 7 steps. Log two times 7.

The division by 220 means that I chose the overall counting to start at 220 Hertz.

The whole exposition to this point has been very dense. I recommend that you switch to the exercise environment. Work on the example to understand it.

# 3
Cljtiles
The exercise environmet is where you gain a thorough understanding of the example. Like in the presentation space, you can take asunder the formula. Moreover you can execute and see the result. You can also inspect the different parts of the formula.
What assists you the most on your path to understanding are the puzzles. Solve them in the easy mode first to see how the formula comes about. You are helped by the coloring of the code. Then shuffle the puzzle and try again until you know the formula.

# 5a
## 1

In other words, the specific log2 curve I chose to fit the notes of the musical scale crosses the x axis at 220. But this choice was arbitrary. 2 Parallel 3 step size.

## 2

There are many of them with different step sizes and intersections. As a demonstration, I draw some of those other variants.

# 5b
In order do be able to draw them, a general formula is needed. It contains two parameters called intersect and steps.
If we set the steps and intersect to one, what remains is the simple plain log2.

again, the really important point is that you practice yourself.

# 5c
But not only that, we also need to define a function, called log2a
880 Hertz happens to be step 14
of course we can calculate much simpler stuff 1 1 8
and if we set step and interect to one, they cancel out of the formula and we are left with the simple log2(8)
if we set the steps wo one, we do not need this block anymore.
Also intersect is not needed.

So if we demand there there are 7 steps if we double the frequency and we go from 220 to 440 the result is indeed 7. That is reassuring.

You can also make a chekc of a much simpler case. going from 1 to 2 with stepsize 1. The result is 1.
This case actually reduces to the simple log2 function. (show in blocks)

You can also easily type this formula into Python. It will understand it when you use the SymPy package. Essentialy SymPy means that you can print nice formulas that contain the symbol x.



# 6
To proof that my new function indeed reproduces the note-log, I calculate the pitch-step for 880Hz. It is step 14. Which is correct, because it takes 14 to go from not A3 to note A5. So my new function log2a passes this sanity check.
One more comment: in my new function, the parameters for the steps and intersection are on an equal footing with the variable x that stand for the frequency. I will change that later on.

# 7
If you choose a step-size of one and also choose the intersection point as one, then you get the standard log2 function of Python.

So I choose the step size of one --1
and the intersection as one --1
and subtract the standard function log2

# 8
and get the result zero. For the following it is important to notice, that the standard log2 function takes a single argument only. In contrast to this, my log2a function takes three arguments, that is first the number of steps, second the point of intersection and third the frequency x itself. The standard function log2 only takes one argument, namely the frequency x.

# 9
One way to show that the standard log2 function indeed has step size 1 is to plot the log2 function. And you can see that the intersection at point x = 1 is very plausible by looking at the graph.
Also, it looks like step 3 is 8 and step 4 is 16 and step 5 is 32.

But to be sure, you have to do the calculations. There a new function arrives on the scene: the mapv function.

# 10a
In this episode, the log2 steps down in its main role and leaves the center stage to mapv. The new and main protagonist in this episode is mapv.

# 10b
mapv is a function that takes two arguments. --2
The first argument is - and that is new - the first argument of mapv is itself a function. --1
mapv takes a function of exactly one parameter, this is good, because the simple log2 fits this
The second argument is a vector of numbers. --4
Here this vector contains the numbers 1 2 4 8 --4
What mapv does is that it calculates the log2 of those numbers
So mapv returns a vector of numbers.

# 11
As you can see, the log2 of 1 is indeed zero. This means that the graph really intersects at exactly zero. Also, the step size is exacty one, the steps are zero, one, two, three.

# 12
The main protagonist of this eposiode, mapv, takes functions with only one single argument. The function of the previous episode, log2a, takes three arguments. So those two do not fit together. But  log2a can be transformed in a way that makes it acceptable by mapv. This transformation is done by a helper function  called partial.

# 14

Ultimately, we'd like to get back to arbitrary step sizes, not just 1 but mybe 7 like with the notes before.

The function partial takes a function as its first parameter (like mapv). It feeds this function partially with numbers, but leaves the last one open. Thus partial takes a function and returns a new function that actually has no name on its own. We will give it a name in due course. But in priciple, such an anonymous function is not a problem, as this new function is taken under its umbrella by mapv.

# 15
Using this combination of mapv and partial, we verify the step-size 7

# 16a
Always calling the partial function is cumbersome. So you'd like to give the new function a name after all. I babtize it log2b and set the intersection at the fixed point zero, only the steps can be set as a parameter.

# 17a
We must not forget that this all not only works with numbers but with symbols as well.

# 17
I check that this new log2b function behaves as expected. I use mapv to call the function now names log2b with a vector of three numbers. I feed the steps of seven and indeed there are always seven steps when the x doubles.

# 18
To call log2b with only one number, I use the function with the name call.

# 20
Solution to 7

# 21b
Wile log2 was invented in India during Europs middle ages, The ln is due Euler in the 17th century.

# Random
All of our natural science is based on those triangles. Choosing the position and size of the triangles is the art of the scientist. But the shape of those trianges seems to be universally given by nature.

Writing the text takes long

# Todo
offline use of blockly
copy
https://raw.githubusercontent.com/mentat-collective/fdg-book/main/clojure/org/chapter001.org
