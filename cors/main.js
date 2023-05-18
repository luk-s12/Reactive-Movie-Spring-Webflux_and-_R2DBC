'use strict';

function testCors() {

    fetch('http://localhost:8080/api/v2/movies/welcome', {
        method: 'GET'
    })
        .then(response => response.text())
        .then(data => {
            const message =
                `
                    <div style="text-align: center;">
                        <h1>${data}</h1>
                    </div>
                    `;
            document.getElementById('response').innerHTML = message;
        });
}

testCors();


var movies = new EventSource("//localhost:8080/api/v2/movies/sse");

movies.onmessage = (event) => {
    const movies = document.getElementById("movies");
    const movie = document.createElement("li");

    movies.style.width = "90%";
    movies.style.margin = "1em auto";

    movie.style.margin = "1em auto";
    movie.textContent = `message: ${event.data}`;

    movies.appendChild(movie);
};

setTimeout(() => movies.close(), "5000");



