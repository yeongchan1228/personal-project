import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { PATH } from "./constants/path";
import Test from "./pages/Test/Test";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={PATH.HOME} element={<div>main Page!!</div>}></Route>
        <Route path={PATH.TEST} element={<Test />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
