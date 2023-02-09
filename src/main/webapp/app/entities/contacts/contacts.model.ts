import dayjs from 'dayjs/esm';

export interface IContacts {
  id: number;
  name?: string | null;
  contactPref?: string | null;
  contactType?: string | null;
  contact?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  refTableType?: string | null;
  refTableId?: number | null;
  companyId?: number | null;
}

export type NewContacts = Omit<IContacts, 'id'> & { id: null };
