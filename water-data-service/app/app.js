require("dotenv").config();
const express = require("express");
const Database = require("./database/database");
const app = express();
const port = process.env.PORT || 3000;
const waterDataRoute = require("./routes/waterDataRouter");

const db = new Database();

app.use(express.json());

app.locals.db = db;

app.use("/waterData", waterDataRoute);

app.listen(port, () => {
    console.log(`server is listening on port ${port}`);
})