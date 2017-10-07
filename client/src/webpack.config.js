import path from "path";

export default {
  entry: {
    app: ["./src/app/index.js"]
  },
  output: {
    path: path.resolve(__dirname, "build"),
    publicPath: "/assets/",
    filename: "bundle.js"
  },
  module: {
    loaders: [
      {
        test: /.js$/,
        loader: 'babel-loader'
      },
      {
        test: /.vue$/,
        loader: 'vue-loader'
      }
    ]
  }
};