# Code-To-Work: Puzzle 8

Here is a physical example of this classic game:

<center>
![classic puzzle 15]
</center>

If you have not seen this game before, each tile can slide either horizontally or vertically into the empty space in order to reorder the tiles.

For our version of this game, we will not be using numbered tiles but a photo taken by the user with the phone’s camera. This is what the UI will look like with an image of a part of Google’s (old) logo:

<center>
![screenshot]
</center>

You can read more about the game on [Wikipedia].

### Tour of the code

The [starter code] for this activity is composed of four classes:

<ul>
<li>
`PuzzleActivity` which is the lone activity for this program:
<ul>
<li>
`onCreate`: in addition to the boilerplate, the given implementation programmatically adds the `PuzzleBoardView` object to the UI.
</li>
<li>
`onCreateOptionsMenu`, `onOptionsItemSelected`: just boilerplate
</li>
<li>
`dispatchTakePictureIntent`: handler for the `Take Photo` button
</li>
<li>
`onActivityResult`: handler for the system call when the photo taking is complete.
</li>
<li>
<code>shuffleImag

  [queue]: https://en.wikipedia.org/wiki/Queue_(abstract_data_type)
  [heap]: https://en.wikipedia.org/wiki/Heap_(data_structure)
  [priority queues]: http://algs4.cs.princeton.edu/24pq/
  [University of Auckland]: https://www.cs.auckland.ac.nz/software/AlgAnim/heaps.html
  [Intents]: http://developer.android.com/guide/components/intents-filters.html
  [classic puzzle 15]: https://cswithandroid.withgoogle.com/content/assets/img/15-puzzle-02.jpg
  [screenshot]: https://cswithandroid.withgoogle.com/content/assets/img/15puzzleapp.png
  [Wikipedia]: https://en.wikipedia.org/wiki/15_puzzle
  [starter code]: https://cswithandroid.withgoogle.com/content/assets/img/Puzzle8_starter.zip
