const express = require("express");
const app = express();
const port = 5001;
const airTempRoute = require("./routes/airTempRouter");

app.use(express.json());

app.use("/airTemp", airTempRoute);

app.listen(port, () => {
    console.log(`server is listening on port ${port}`);
})