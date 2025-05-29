# 1
## 1
Here you see the notes. Thy span two octaves.
https://www.youtube.com/watch?v=SArAehjpMyk
/Users/kloimhardt/klmtemp/site/mysite/audio/violinA3A5.m4a

## 2
Here the distance from A to A are equal

## 3
But if you draw the notes according to their frequencies, then the distance from A3 to A4 is half the distance from A4 to A5

## 4
That is: A3 is at the frequency of 220Hz, A4 is at 440Hz and A5 is at 880Hz

## 5
The function that fits the notes is the logarithm

# 1a
The red graph is drawn with a standard Python function called log2. It is the logarithm function, as described in the docstring of this function. But I'd like to close this and move to a pictorial reprersentation of code.

# 2
The exact formula to draw the red graph is the following: log2 of x divided by 220 times 7.

You might want to open up the formua to get a deeper understanding. --P4--

x is the frequency
It is divided by 220
Then the logarithm is taken.
The whole thing is multiplied by seven.
The 2 in the logarithm and the multiplication by 7  means that the frequency is doubled every 7th step.
So if we start at A4, which is at 440 Hertz, and go upwards 7 steps 1 2 3 4 5 6 7, we land at at A5 which is 880 Hertz. Double frequency every 7 steps. Log two times 7.

The division by 220 means that the ground floor starts counting at zero and is at 220 Hertz.

# 3
You can see this again in the graph for the formla. The graph crosses the x-axis, our ground floor, at 220 and gets flatter and flatter out to the right so that it reaches double the frequency every 7th step.

# 4
You can also easily type this formula into Python. It will understand it when you use the SymPy package. Essentialy SymPy means that you can print nice formulas that contain the symbol x.

# 5a
There is of course not only one Log, there are many of them with different step sizes and intersections. I can draw some of them.

# 5b
In order do be able to draw them, a general formula is needed

# 5c
But not only that, we also need to define a function, called log2a

# 6
To proof that my new function indeed reproduces the note-log, I calculate the pitch-step for 880Hz. It is step 14. Which is correct, because it takes 14 to go from not A3 to note A5. So my new function log2a passes this sanity check.
One more comment: in my new function, the parameters for the steps and intersection are on an equal footing with the variable x that stand for the frequency. I will change that later on.

# 7
If you choose a step-size of one and also choose the intersection point as one, then you get the standard log2 function of Python.

So I choose the step size of one --1
and the intersection as one --1
and subtract the standard function log2

# 8
and get the result zero

# 20
Solution to 7

All of our natural science is based on those triangles. Choosing the position and size of the triangles is the art of the scientist. But the shape of those trianges seems to be universally given by nature.
