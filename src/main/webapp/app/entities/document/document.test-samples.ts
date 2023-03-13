import dayjs from 'dayjs/esm';

import { IDocument, NewDocument } from './document.model';

export const sampleWithRequiredData: IDocument = {
  id: 70614,
};

export const sampleWithPartialData: IDocument = {
  id: 30230,
  docType: 'Berkshire Future',
  docCategory: 'connect',
  fileName: 'Towels Cuban Cambridgeshire',
  contentType: 'Branding Mountains drive',
  fileUuid: 'monitor forecast User-friendly',
  folderUuid: 'Forward B2C',
  refTableId: 44142,
  status: 'Account Keyboard Metal',
  lastModified: dayjs('2023-03-12T16:24'),
};

export const sampleWithFullData: IDocument = {
  id: 83698,
  docType: 'Indian syndicate Canada',
  docCategory: 'capacitor redundant',
  fileName: 'Bermuda',
  contentType: 'Sausages high-level',
  fileUuid: 'Utah Account system',
  folderUuid: 'Salvador Soft',
  refTable: 'Won Lek',
  refTableId: 93409,
  companyId: 34391,
  status: 'Investor',
  lastModified: dayjs('2023-03-12T09:16'),
  lastModifiedBy: 'FTP',
};

export const sampleWithNewData: NewDocument = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
