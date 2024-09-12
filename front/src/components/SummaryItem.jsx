import React from "react";

export function SummaryItem({productName, count}) {
  return (
    <div className="row mx-2">
      <h6 className="p-0">{productName} <span className="badge bg-dark text-">{count}개</span></h6>
    </div>
  )
}