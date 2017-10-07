var express = require("express");
var webpack = require("webpack");
var webpackMiddleware = require("webpack-dev-middleware");
var path = require("path");
var webpackHotMiddleware = require("webpack-hot-middleware");

var config = require("./webpack.config");

var app = express();
var compiler = webpack(config);

app.use(webpackMiddleware(compiler, {
  publicPath: config.output.publicPath,
}));

app.use(webpackHotMiddleware(compiler));

app.use(express.static(path.join(__dirname, "./public")));

app.listen(3000, () => {
  console.log("Server is listening on port 3000!");
});