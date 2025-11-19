import { Button } from "primereact/button"
import "../style.css/ButtonNoStyle.css"

const ButtonNoStyle = ({ label, className, ...props }) => {
  return (
    <Button
      className={`btn ${className || ''}`}
      label={label} // ← Isso que estava faltando!
      {...props}
    />
  )
}

export default ButtonNoStyle
