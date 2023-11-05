// require("dotenv").config();
const express = require("express");
const Database = require("./database/database");
const app = express();
const port = 5000 || 3000;
const waterTempRoute = require("./routes/waterTempRouter");

// const db = new Database();

app.use(express.json());

// app.locals.db = db;

app.use("/waterTemp", waterTempRoute);

app.listen(port, () => {
    console.log(`server is listening on port ${port}`);
})