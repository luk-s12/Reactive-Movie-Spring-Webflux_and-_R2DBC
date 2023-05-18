const movies = new EventSource("//localhost:8080/api/v2/movies/sse/sink");

const form = () => {
    document.getElementById("form").addEventListener("submit", event => {
        event.preventDefault()
        const title = document.getElementById("title").value;
        const description = document.getElementById("description").value;

        save({ title, description })

    });
}

const save = (data) => {
    fetch("http://localhost:8080/api/v2/movies", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
}


const movie = () => {
    movies.onmessage = (event) => {
        const notification = document.getElementById("notification");

        const data = JSON.parse(event.data);

        const element = `
        <div class="table__row">${data.message} </div>
        <div class="table__row">${data.title}</div>
        `;

        notification.insertAdjacentHTML("beforeend", element);
    };
};

form();
movie();

