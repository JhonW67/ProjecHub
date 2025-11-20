import { Button } from "primereact/button"
import "../style.css/ButtonNoStyle.css"

const ButtonNoStyle = ({ label, className, onClick, ...props }) => {
  return (
    <Button
      className={`btn ${className || ''}`}
      label={label} 
      onClick={onClick}
      {...props}
    />
  )
}

export default ButtonNoStyle
