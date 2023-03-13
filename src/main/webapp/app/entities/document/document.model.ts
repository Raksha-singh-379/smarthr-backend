import dayjs from 'dayjs/esm';

export interface IDocument {
  id: number;
  docType?: string | null;
  docCategory?: string | null;
  fileName?: string | null;
  contentType?: string | null;
  fileUuid?: string | null;
  folderUuid?: string | null;
  refTable?: string | null;
  refTableId?: number | null;
  companyId?: number | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export type NewDocument = Omit<IDocument, 'id'> & { id: null };
