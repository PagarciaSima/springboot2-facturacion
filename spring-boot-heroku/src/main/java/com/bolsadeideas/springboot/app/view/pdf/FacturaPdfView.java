package com.bolsadeideas.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	PdfPCell cell = null;
	Font tableTitleFont = FontFactory.getFont(FontFactory.TIMES, Font.DEFAULTSIZE, Font.BOLD);
	Phrase phrase = null;

	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura)model.get("factura");
		Locale locale = localeResolver.resolveLocale(request);
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		PdfPTable table = new PdfPTable(1);
		table.setSpacingAfter(20);
		
		
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		table.addCell(cell);
		table.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		table.addCell(factura.getCliente().getEmail());
		
		PdfPTable table2 = new PdfPTable(1);
		table2.setSpacingAfter(20);
		
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		table2.addCell(cell);
		table2.addCell(mensajes.getMessage("text.cliente.factura.folio") + factura.getId());
		table2.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + factura.getDescripcion());
		table2.addCell(mensajes.getMessage("text.cliente.factura.fecha") + factura.getCreateAt());
		
		PdfPTable table3 = new PdfPTable(4);
		table3.setWidths(new float [] {3.5f, 1, 1, 1});
		phrase = new Phrase(mensajes.getMessage("text.factura.form.item.nombre"), tableTitleFont);
		cell = new PdfPCell(phrase);
		table3.addCell(cell);
		phrase = new Phrase(mensajes.getMessage("text.factura.form.item.precio"), tableTitleFont);
		cell = new PdfPCell(phrase);
		table3.addCell(cell);
		phrase = new Phrase(mensajes.getMessage("text.factura.form.item.cantidad"), tableTitleFont);
		cell = new PdfPCell(phrase);
		table3.addCell(cell);
		phrase = new Phrase(mensajes.getMessage("text.factura.form.item.total"), tableTitleFont);
		cell = new PdfPCell(phrase);
		table3.addCell(cell);
		
		factura.getItems().forEach(item -> {

			table3.addCell(item.getProducto().getNombre());
			table3.addCell(item.getProducto().getPrecio().toString());
			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
			table3.addCell(cell);
			table3.addCell(item.calcularImporte().toString());
		});
		// Footer gran total
		cell = new PdfPCell(new Phrase(mensajes.getMessage("text.factura.form.total")));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
		table3.addCell(cell);
		table3.addCell(factura.getTotal().toString());
		
		document.add(table);
		document.add(table2);
		document.add(table3);

	}

}
