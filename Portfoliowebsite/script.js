// ================================
// 1. LOCAL PORTFOLIO DATA
// ================================
// We store project information in JavaScript objects.
// JavaScript will later turn each object into HTML using DOM manipulation.

const projects = [
  {
    title: "Bank of CLI",
    description:
      "A Java and PostgreSQL terminal banking application with account registration, deposits, withdrawals, transfers, and transaction history.",
    technologies: ["Java", "SQL", "PostgreSQL", "JDBC"],
    category: "Java",
  },
  {
    title: "Bouldering Route Database",
    description:
      "A database-focused project for storing climbing gyms, routes, grades, attempts, and completed climbs.",
    technologies: ["SQL", "PostgreSQL"],
    category: "SQL",
  },
  {
    title: "Portfolio API Dashboard",
    description:
      "A front-end project that calls an external API and dynamically renders results using JavaScript.",
    technologies: ["JavaScript", "REST API", "DOM"],
    category: "JavaScript",
  },
];

const skills = [
  "Java",
  "JavaScript",
  "SQL",
  "PostgreSQL",
  "JDBC",
  "Git",
  "HTML",
  "CSS",
  "REST APIs",
];

// ================================
// 2. SELECT DOM ELEMENTS
// ================================

const projectGrid = document.querySelector("#projectGrid");
const filterButtons = document.querySelectorAll(".filter");
const skillList = document.querySelector("#skillList");

const themeToggle = document.querySelector("#themeToggle");

const githubUsername = document.querySelector("#githubUsername");
const loadReposBtn = document.querySelector("#loadReposBtn");
const repoGrid = document.querySelector("#repoGrid");
const apiStatus = document.querySelector("#apiStatus");

const contactForm = document.querySelector("#contactForm");
const formMessage = document.querySelector("#formMessage");

const copyright = document.querySelector("#copyright");

// ================================
// 3. DOM MANIPULATION: RENDER SKILLS
// ================================

function renderSkills() {
  skillList.innerHTML = "";

  skills.forEach((skillName) => {
    const skill = document.createElement("span");

    skill.classList.add("skill");
    skill.textContent = skillName;

    skillList.appendChild(skill);
  });
}

// ================================
// 4. DOM MANIPULATION: RENDER PROJECTS
// ================================

function renderProjects(projectsToRender) {
  // Clear old cards before drawing the new ones.
  projectGrid.innerHTML = "";

  projectsToRender.forEach((project) => {
    // Create a new article element.
    const card = document.createElement("article");
    card.classList.add("project-card");

    // Create the project's technology tags.
    const tags = project.technologies
      .map((technology) => `<span class="tag">${technology}</span>`)
      .join("");

    // Insert content into the new card.
    card.innerHTML = `
      <h3>${project.title}</h3>
      <p>${project.description}</p>

      <div class="project-meta">
        ${tags}
      </div>
    `;

    // Add the finished card to the webpage.
    projectGrid.appendChild(card);
  });
}

// ================================
// 5. DOM MANIPULATION: FILTER PROJECTS
// ================================

filterButtons.forEach((button) => {
  button.addEventListener("click", () => {
    // Remove "active" from every filter button.
    filterButtons.forEach((btn) => btn.classList.remove("active"));

    // Add "active" to the clicked button.
    button.classList.add("active");

    const selectedCategory = button.dataset.filter;

    if (selectedCategory === "All") {
      renderProjects(projects);
      return;
    }

    const filteredProjects = projects.filter(
      (project) => project.category === selectedCategory
    );

    renderProjects(filteredProjects);
  });
});

// ================================
// 6. DOM MANIPULATION: DARK MODE
// ================================

themeToggle.addEventListener("click", () => {
  document.body.classList.toggle("dark");

  const darkModeIsOn = document.body.classList.contains("dark");

  // Change the icon by changing textContent in the DOM.
  themeToggle.textContent = darkModeIsOn ? "☀️" : "🌙";
});

// ================================
// 7. API INTEGRATION: GITHUB API
// ================================

async function loadGitHubRepos() {
  const username = githubUsername.value.trim();

  if (!username) {
    apiStatus.textContent = "Please enter a GitHub username.";
    repoGrid.innerHTML = "";
    return;
  }

  apiStatus.textContent = "Loading repositories...";
  repoGrid.innerHTML = "";

  try {
    // fetch() sends an HTTP GET request to GitHub's REST API.
    const response = await fetch(
      `https://api.github.com/users/${encodeURIComponent(username)}/repos?sort=updated&per_page=6`
    );

    // A 404 or another error response does not automatically throw an error,
    // so we check response.ok ourselves.
    if (!response.ok) {
      throw new Error(`GitHub request failed with status ${response.status}`);
    }

    // Convert the JSON response into JavaScript objects.
    const repositories = await response.json();

    if (repositories.length === 0) {
      apiStatus.textContent = "No public repositories found.";
      return;
    }

    apiStatus.textContent = `Showing ${repositories.length} recent public repositories.`;

    // DOM manipulation: create a card for every repo returned by the API.
    repositories.forEach((repo) => {
      const card = document.createElement("article");
      card.classList.add("project-card");

      card.innerHTML = `
        <h3>${escapeHTML(repo.name)}</h3>
        <p>
          ${escapeHTML(repo.description || "No description provided.")}
        </p>

        <div class="project-meta">
          <span class="tag">${escapeHTML(repo.language || "N/A")}</span>
          <span class="tag">⭐ ${repo.stargazers_count}</span>
          <span class="tag">🍴 ${repo.forks_count}</span>
        </div>

        <a
          class="project-link"
          href="${repo.html_url}"
          target="_blank"
          rel="noopener noreferrer"
        >
          View Repository →
        </a>
      `;

      repoGrid.appendChild(card);
    });
  } catch (error) {
    console.error(error);

    apiStatus.textContent =
      "Could not load repositories. Check the username or try again later.";
  }
}

// Clicking the button calls the API.
loadReposBtn.addEventListener("click", loadGitHubRepos);

// Pressing Enter inside the username input also calls the API.
githubUsername.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    loadGitHubRepos();
  }
});

// Basic escaping for API-provided text before placing it in innerHTML.
function escapeHTML(value) {
  return String(value)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

// ================================
// 8. DOM MANIPULATION: FORM FEEDBACK
// ================================

contactForm.addEventListener("submit", (event) => {
  // Prevent the page from refreshing.
  event.preventDefault();

  const name = document.querySelector("#name").value.trim();

  // Change text on the page dynamically.
  formMessage.textContent = `Thanks, ${name}! Your demo message was received.`;

  // Clear the form fields.
  contactForm.reset();
});

// ================================
// 9. DOM MANIPULATION: CURRENT YEAR
// ================================

copyright.textContent =
  `© ${new Date().getFullYear()} Gian Obenita | Built with HTML, CSS, JavaScript, and the GitHub API.`;

// ================================
// 10. INITIAL PAGE LOAD
// ================================

renderSkills();
renderProjects(projects);
loadGitHubRepos();
