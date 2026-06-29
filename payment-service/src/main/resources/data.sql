INSERT INTO bills (bill_reference, provider, subscriber_name, amount, paid, bill_date) VALUES
('ISM-2024-001', 'ISM', 'Amadou Diallo', 150000.00, false, CURRENT_DATE),
('ISM-2024-002', 'ISM', 'Fatou Ndiaye', 120000.00, false, CURRENT_DATE),
('ISM-2024-003', 'ISM', 'Moussa Sow', 180000.00, true, DATEADD('MONTH', -2, CURRENT_DATE)),
('WOY-2024-001', 'WOYAFAL', 'Ibrahima Ba', 25000.00, false, CURRENT_DATE),
('WOY-2024-002', 'WOYAFAL', 'Aissatou Fall', 18000.00, false, CURRENT_DATE),
('WOY-2024-003', 'WOYAFAL', 'Omar Gueye', 32000.00, true, DATEADD('MONTH', -1, CURRENT_DATE));
