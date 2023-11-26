const express = require("express");
const app = express();
const port = 5004;
const logsRoute = require("./routes/logsRouter");

app.use(express.json());

app.use("/logs", logsRoute);

app.listen(port, () => {
    console.log(`server is listening on port ${port}`);
    console.log(Date.now().toString());
})