import express from "express";
import webpack from "webpack";
import webpackMiddleware from "webpack-dev-middleware";
import path from "path";

import config from "./webpack.config.js";

const app = express();
const compiler = webpack(config);

app.use(webpackMiddleware(compiler, {
  publicPath: config.output.publicPath,
}));

app.use(express.static(path.join(__dirname, "../public")));

app.get("*", (req, res) => {
  res.sendFile(path.join(__dirname, "../public/index.html"));
});

app.listen(3000, () => {
  console.log("Server is listening on port 3000!");
});