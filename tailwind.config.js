/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["filmbox/src/main/resources/templates/*.{html,js}"],
  theme: {
    extend: {},
  },
  plugins: [],
}

// npx tailwindcss -i filmbox/src/main/resources/static/input.css -o filmbox/src/main/resources/static/output.css --watch