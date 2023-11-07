const express = require("express");
const app = express();
const port = 5000 || 3000;
const waterTempRoute = require("./routes/waterTempRouter");

app.use(express.json());

app.use("/waterTemp", waterTempRoute);

app.listen(port, () => {
    console.log(`server is listening on port ${port}`);
})