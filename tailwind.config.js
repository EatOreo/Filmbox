/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: "class",
  content: [
    "filmbox/src/main/resources/templates/*.{html,js}", 
    "filmbox/src/main/resources/templates/api/*.{html,js}",
    "filmbox/src/main/resources/films.json",
    "filmbox/src/main/resources/static/*.{html,js}",
  ],
  theme: {
    extend: {
      aspectRatio: {
        'film': '23 / 9',
      },
    },
  },
  plugins: [],
}

// npx tailwindcss -i input.css -o filmbox/src/main/resources/static/output.css --watch