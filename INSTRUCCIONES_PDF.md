# 📄 INSTRUCCIONES PARA CONVERTIR A PDF

## 🎯 Opción 1: Usar el Script Automático

### Requisitos:
1. **Python 3.6+** instalado
2. **wkhtmltopdf** instalado (descargar desde https://wkhtmltopdf.org/downloads.html)
3. **Librerías Python:**
   ```bash
   pip install markdown2 pdfkit
   ```

### Ejecutar:
```bash
python convert_to_pdf.py
```

### Resultado:
- `DOCUMENTACION_TONTUFOS2.pdf`
- `CODIGO_COMENTADO.pdf`

---

## 🎯 Opción 2: Conversión Manual

### Usando Pandoc (Recomendado):
```bash
# Instalar Pandoc
# Windows: https://pandoc.org/installing.html
# Mac: brew install pandoc
# Linux: sudo apt install pandoc

# Convertir documentación
pandoc DOCUMENTACION_TONTUFOS2.md -o DOCUMENTACION_TONTUFOS2.pdf

# Convertir código comentado
pandoc CODIGO_COMENTADO.md -o CODIGO_COMENTADO.pdf
```

### Usando Markdown Viewer Online:
1. Ir a https://www.markdowntopdf.com/
2. Subir el archivo `.md`
3. Descargar el PDF

### Usando VS Code:
1. Instalar extensión "Markdown PDF"
2. Abrir archivo `.md`
3. Ctrl+Shift+P → "Markdown PDF: Export (pdf)"

---

## 🎯 Opción 3: Herramientas Online

### Conversores Online:
- **Markdown to PDF:** https://www.markdowntopdf.com/
- **Dillinger:** https://dillinger.io/
- **StackEdit:** https://stackedit.io/

### Pasos:
1. Copiar contenido del archivo `.md`
2. Pegar en el conversor online
3. Exportar como PDF

---

## 📁 Archivos a Convertir

### Documentación Principal:
- `DOCUMENTACION_TONTUFOS2.md` → `DOCUMENTACION_TONTUFOS2.pdf`
- `CODIGO_COMENTADO.md` → `CODIGO_COMENTADO.pdf`

### Archivos Adicionales:
- `README_PARA_COMPAÑERO.md` → `README_PARA_COMPAÑERO.pdf`
- `INSTRUCCIONES_PDF.md` → `INSTRUCCIONES_PDF.pdf`

---

## 🎨 Personalización del PDF

### Si usas el script Python:
Edita `convert_to_pdf.py` y modifica la sección `css_styles` para cambiar:
- Fuentes
- Colores
- Espaciado
- Estilos de código

### Si usas Pandoc:
```bash
# Con plantilla personalizada
pandoc input.md -o output.pdf --template=template.tex

# Con CSS personalizado
pandoc input.md -o output.pdf --css=style.css
```

---

## 🚨 Solución de Problemas

### Error: "wkhtmltopdf not found"
```bash
# Windows: Añadir wkhtmltopdf al PATH
# Mac: brew install wkhtmltopdf
# Linux: sudo apt install wkhtmltopdf
```

### Error: "markdown2 not found"
```bash
pip install markdown2
```

### Error: "pdfkit not found"
```bash
pip install pdfkit
```

### Caracteres especiales no se ven bien:
- Asegúrate de que el archivo esté en UTF-8
- Usa fuentes que soporten caracteres especiales

---

## 📋 Checklist de Conversión

- [ ] Instalar herramientas necesarias
- [ ] Verificar que los archivos `.md` existen
- [ ] Ejecutar conversión
- [ ] Verificar que los PDFs se generaron correctamente
- [ ] Revisar que el formato se ve bien
- [ ] Comprobar que las imágenes/emojis se muestran

---

## 🎯 Recomendación Final

**Para tu compañero, recomiendo:**
1. Usar el script automático si tiene Python
2. Si no, usar Pandoc (más rápido y confiable)
3. Como última opción, usar conversores online

Los PDFs generados tendrán:
- ✅ Formato profesional
- ✅ Código resaltado
- ✅ Emojis y caracteres especiales
- ✅ Navegación por secciones
- ✅ Fácil de imprimir y compartir
