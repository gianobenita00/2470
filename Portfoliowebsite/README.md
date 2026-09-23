# Portfolio Website: API Integration + DOM Manipulation

This is a beginner-friendly portfolio project built with:

- HTML
- CSS
- Vanilla JavaScript
- GitHub REST API

## What demonstrates API integration?

In `script.js`, the `loadGitHubRepos()` function uses:

```js
fetch(`https://api.github.com/users/${username}/repos`)
```

The program waits for the GitHub API response, converts the JSON into JavaScript objects, and displays the repositories on the page.

## What demonstrates DOM manipulation?

The project uses JavaScript to:

- Create project cards with `document.createElement()`
- Add elements with `appendChild()`
- Select elements with `querySelector()`
- Change text using `textContent`
- Change HTML using `innerHTML`
- Add/remove CSS classes with `classList`
- Filter displayed projects
- Toggle dark mode
- Display contact-form feedback
- Insert the current year automatically
- Render live GitHub API data

## Run it

Open `index.html` in your browser.

For the cleanest development workflow, open the folder in VS Code and use the Live Server extension.

## Customize it

Replace the sample name, about text, project data, and skills.

In `index.html`, change:

```html
value="octocat"
```

to your GitHub username if you want your repositories to load automatically.

## Interview explanation

You can say:

> "I built a portfolio using vanilla JavaScript. I integrated the GitHub REST API using fetch and async/await, then converted the JSON response into JavaScript objects. I used DOM manipulation to dynamically create repository cards, filter project data, toggle dark mode, update form feedback, and change page content without reloading the page."
