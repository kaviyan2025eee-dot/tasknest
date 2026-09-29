const API_URL = "http://localhost:8080/api";


// ===============================
// LOAD ALL TASKS
// ===============================
async function loadTasks() {

    try {

        const response = await fetch(`${API_URL}/tasks`);

        if (!response.ok) {
            throw new Error("Failed to load tasks");
        }

        const tasks = await response.json();

        displayTasks(tasks);

    } catch (error) {

        console.error(error);

        document.getElementById("taskContainer").innerHTML =
            "<p>Unable to load tasks. Please check the backend.</p>";
    }
}


// ===============================
// DISPLAY TASKS
// ===============================
function displayTasks(tasks) {

    const container = document.getElementById("taskContainer");

    container.innerHTML = "";

    if (tasks.length === 0) {

        container.innerHTML = "<p>No tasks found.</p>";
        return;
    }

    tasks.forEach(task => {

        const card = document.createElement("div");

        card.className = "task-card";

        if (task.completed) {
            card.classList.add("completed");
        }

        card.innerHTML = `
            <h3>${task.title}</h3>

            <p>
                <strong>Due Date:</strong>
                ${task.dueDate || "No due date"}
            </p>

            <p>
                <strong>Priority:</strong>
                ${task.priority}
            </p>

            <p>
                <strong>List:</strong>
                ${task.taskList ? task.taskList.name : "Unknown"}
            </p>

            <p>
                <strong>Status:</strong>
                ${task.completed ? "Completed" : "Pending"}
            </p>

            <button onclick="toggleTask(${task.id}, ${task.completed})">
                ${task.completed ? "Mark Incomplete" : "Mark Complete"}
            </button>

            <button onclick="moveTask(${task.id})">
                Move Task
            </button>

            <button onclick="deleteTask(${task.id})">
                Delete
            </button>
        `;

        container.appendChild(card);
    });
}


// ===============================
// ADD TASK
// ===============================
async function addTask() {

    const title = document.getElementById("title").value;
    const dueDate = document.getElementById("dueDate").value;
    const priority = document.getElementById("priority").value;
    const taskListId = document.getElementById("taskList").value;


    if (!title) {

        alert("Please enter task title");
        return;
    }


    const task = {

        title: title,

        dueDate: dueDate || null,

        priority: priority,

        taskList: {
            id: Number(taskListId)
        }
    };


    try {

        const response = await fetch(`${API_URL}/tasks`, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(task)
        });


        if (!response.ok) {

            const message = await response.text();

            alert(message);

            return;
        }


        alert("Task added successfully!");


        document.getElementById("title").value = "";
        document.getElementById("dueDate").value = "";


        loadTasks();

    } catch (error) {

        console.error(error);

        alert("Unable to add task.");
    }
}


// ===============================
// COMPLETE / INCOMPLETE TASK
// ===============================
async function toggleTask(id, completed) {

    const endpoint = completed
        ? `${API_URL}/tasks/${id}/incomplete`
        : `${API_URL}/tasks/${id}/complete`;


    try {

        const response = await fetch(endpoint, {

            method: "PATCH"
        });


        if (!response.ok) {

            throw new Error("Unable to update task");
        }


        loadTasks();

    } catch (error) {

        console.error(error);

        alert("Unable to update task.");
    }
}


// ===============================
// DELETE TASK
// ===============================
async function deleteTask(id) {

    const confirmDelete =
        confirm("Are you sure you want to delete this task?");


    if (!confirmDelete) {
        return;
    }


    try {

        const response = await fetch(
            `${API_URL}/tasks/${id}`,
            {
                method: "DELETE"
            }
        );


        if (!response.ok) {

            throw new Error("Unable to delete task");
        }


        loadTasks();

    } catch (error) {

        console.error(error);

        alert("Unable to delete task.");
    }
}


// ===============================
// MOVE TASK
// ===============================
async function moveTask(taskId) {

    const newListId =
        prompt("Enter Task List ID (1 = Academics, 2 = Personal):");


    if (!newListId) {
        return;
    }


    try {

        const response = await fetch(
            `${API_URL}/tasks/${taskId}/move/${newListId}`,
            {
                method: "PATCH"
            }
        );


        if (!response.ok) {

            const message = await response.text();

            alert(message);

            return;
        }


        alert("Task moved successfully!");

        loadTasks();

    } catch (error) {

        console.error(error);

        alert("Unable to move task.");
    }
}


// ===============================
// TODAY TASKS
// ===============================
async function loadTodayTasks() {

    try {

        const response =
            await fetch(`${API_URL}/tasks/today`);


        if (!response.ok) {
            throw new Error("Unable to load today's tasks");
        }


        const tasks = await response.json();

        displayTasks(tasks);

    } catch (error) {

        console.error(error);

        alert("Unable to load today's tasks.");
    }
}


// ===============================
// OVERDUE TASKS
// ===============================
async function loadOverdueTasks() {

    try {

        const response =
            await fetch(`${API_URL}/tasks/overdue`);


        if (!response.ok) {
            throw new Error("Unable to load overdue tasks");
        }


        const tasks = await response.json();

        displayTasks(tasks);

    } catch (error) {

        console.error(error);

        alert("Unable to load overdue tasks.");
    }
}


// ===============================
// LOAD TASKS WHEN PAGE OPENS
// ===============================
loadTasks();