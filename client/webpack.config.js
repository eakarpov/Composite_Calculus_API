var path = require("path");

module.exports = {
  entry: {
    app: ["./src/app/init.ts"]
  },
  output: {
    path: path.resolve(__dirname, "build"),
    publicPath: "/static/",
    filename: "bundle.js"
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js']
  },
  devtool: 'source-map',
  module: {
    loaders: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
            ts: 'ts-loader'
          },
          esModule: true
        }
      },
      {
        test: /\.tsx?$/,
        loader: 'ts-loader',
        options: {
          configFile: 'tsconfig.json',
          appendTsSuffixTo: [/\.vue$/]
        },
      },
      {
        test: /\.tsx$/,
        loader: 'babel-loader'
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/
      },
      {
        test: /\.scss$/,
        loaders: [
          'style-loader',
          'css-loader',
          'sass-loader'
        ]
      }
    ]
  }
};