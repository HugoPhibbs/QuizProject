# First Debrief

By @HugoPhibbs

For deliverables finished on 31st July 2021.

## What went well

- Overall I thought the source code is a strong point for this project. I tried to separate things as much as possible into functions. This was different than the last project that we did (IslandTrader) where the use of functions wasn't consistent, i.e. with a recognizable style. Some methods ended up being about 50 lines long (e.g. Store.sellItemToPlayer(..)), with this project, I tried to split individual steps of a larger process into smaller digestible functions. This made things easy to read, debug and refactor.
- Documentation I felt was another strong point of this project. I tried to keep the JavaDoc up to speed every time I push new changes to the baseline. Documentation made writing new code that integrated existing modules much easier. For example, if I forgot what the purpose of some obscure function was, I could easily look up the documentation and quickly get an understanding.
- Task management also greatly helped with this project. Instead of remembering everything that I had to do, I instead logged this into Asana. So once I was done with one task, I could simply open up Asana and see what I needed to do next.
- Unit testing was a very powerful tool to give me faith that the code that I had written on a small scale would integrate well with other modules. If I didn't do constant unit testing, I think I would've struggled to do integration testing, as I would need to constantly, fix small bugs creating much bigger errors. JUnit testing discovered multiple bugs that I quickly fixed and tested to see if they were patched. Additionally, just before I made a push, I ran unit tests, just to make sure that the latest changes that I made didn't just break the build!

## What didn't go well

- Time management. The project was delivered well beyond the deadline I set. However, this was because I added a new extra feature which greatly lengthened the time needed to finish the project. This was GuiManager, when I implemented this, it created unexpected bugs that took a while to fix. I guess this is a lesson to make sure that your code is easy to integrate with new changes, although I thought that my code was written well, it did expose some weaknesses. Had I not implemented GuiManager, I think the project could've been delivered just a few days over time, not a couple of weeks. That being said, I did have university in the last 2 weeks of this project, which slowed progress significantly.

## Next Steps

- It would be interesting to create new game modes for testing. One idea is to have a multi choice format, eg 4 options, a user tries to pick the correct one.
- Additionally, it could be useful for Decks to be grouped into groups reflecting relevance to each other. This will make it easier to organize Decks within the app.
- Would be nice to have a way for the app to load the last session automatically every time that you run it, without choosing the working directory every time.
- Could have action listeners for key pressed. Eg pressing enter can do certain things depending on what Screen is currently being shown.
