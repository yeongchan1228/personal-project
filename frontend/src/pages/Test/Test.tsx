import { useEffect, useState } from "react";

export default function Test() {
  const [data, setData] = useState("data");
  useEffect(() => {
    fetch("/api/v1/test")
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setData(data.data);
      })
      .catch((error) => console.log(error));
  }, []);
  return (
    <div>
      <h4>{data}</h4>
    </div>
  );
}
